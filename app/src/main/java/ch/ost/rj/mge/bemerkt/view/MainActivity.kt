package ch.ost.rj.mge.bemerkt.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.ost.rj.mge.bemerkt.NotesListAdapter
import ch.ost.rj.mge.bemerkt.R
import ch.ost.rj.mge.bemerkt.SwipeToDelete
import ch.ost.rj.mge.bemerkt.model.Note
import ch.ost.rj.mge.bemerkt.model.NotesDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() , CoroutineScope {
    private var noteDB: NotesDatabase? = null
    private var adapter: NotesListAdapter? = null
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mJob = Job()

        noteDB = NotesDatabase.getDatabase(this)
        adapter = NotesListAdapter(this, noteDB!!)

        initRecyclerView()

        new_note.setOnClickListener(View.OnClickListener { view ->
            startActivity(EditActivity.createIntent(this))
        })

    }

    private fun initRecyclerView(){
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))

        val itemTouchHelper = ItemTouchHelper(
            SwipeToDelete(
                adapter!!
            )
        )
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onResume() {
        super.onResume()
        getAllNotes()
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }

    private fun getAllNotes() {
        launch {
            val listNotes: List<Note>? = noteDB?.noteDao()?.getAllNotes()
            if (listNotes != null) {
                adapter?.setNotes(listNotes)
            }
        }
    }
}

