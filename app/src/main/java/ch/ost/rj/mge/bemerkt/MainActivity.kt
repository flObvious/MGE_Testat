package ch.ost.rj.mge.bemerkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        new_note.setOnClickListener(View.OnClickListener { view ->
            startActivity(EditActivity.createIntent(this))
        })

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

