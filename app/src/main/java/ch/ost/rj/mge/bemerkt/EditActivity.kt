package ch.ost.rj.mge.bemerkt

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ch.ost.rj.mge.bemerkt.model.NoteRepository
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*

class EditActivity : AppCompatActivity() {
    private lateinit var noteRepository: NoteRepository
    lateinit var title: String
    lateinit var content: String

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, EditActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        save_note.setOnClickListener(View.OnClickListener { view ->
            finish()
        })
    }
}