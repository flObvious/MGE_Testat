package ch.ost.rj.mge.bemerkt

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        val note: Note? = intent.getParcelableExtra("note_data")
        setContent(note)

        mJob = Job()
        noteDB = NotesDatabase.getDatabase(this)

        save_note.setOnClickListener {
            if(note == null){
                insertNote(note_title.text.toString(), note_content.text.toString())
            }else{
                Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show()
                updateNote(note_title.text.toString(), note_content.text.toString(), note)

            }
            finish()
        }
    }

    private fun setContent(note: Note?){
        note_title.setText(note?.title)
        note_content.setText(note?.desc)
    }

    private fun insertNote(title: String, description: String){
        if(!title.isEmpty() && !description.isEmpty()){
            launch {
                noteDB?.noteDao()?.insert(Note(title = title, desc = description))
            }
        }
    }

    private fun updateNote(title: String, description: String, note: Note){
        note.title = title
        note.desc = description
        if(!title.isEmpty() && !description.isEmpty()){
            launch {
                noteDB?.noteDao()?.update(note)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}