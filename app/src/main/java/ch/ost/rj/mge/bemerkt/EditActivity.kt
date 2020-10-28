package ch.ost.rj.mge.bemerkt

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.ost.rj.mge.bemerkt.model.Note
import ch.ost.rj.mge.bemerkt.model.NotesDatabase
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EditActivity : AppCompatActivity(), CoroutineScope {


    private var noteDB : NotesDatabase?= null
    private lateinit var mJob: Job

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, EditActivity::class.java)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        mJob = Job()
        noteDB =
            NotesDatabase.getDatabase(this)

        save_note.setOnClickListener {
            val title: String = note_title.text.toString()
            val description: String = note_content.text.toString()

            if(!title.isEmpty() && !description.isEmpty()){
                launch {
                    noteDB?.notesDao()?.insert(Note(title = title, desc = description))

                }
            }
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}