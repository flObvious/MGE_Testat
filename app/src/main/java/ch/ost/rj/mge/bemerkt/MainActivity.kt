package ch.ost.rj.mge.bemerkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        new_note.setOnClickListener(View.OnClickListener { view ->
            intent = Intent(this, EditActivity::class.java)
            startActivity(intent)

        })
    }
}