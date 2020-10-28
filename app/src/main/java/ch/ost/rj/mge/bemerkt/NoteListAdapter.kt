package ch.ost.rj.mge.bemerkt

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import ch.ost.rj.mge.bemerkt.model.Note
import ch.ost.rj.mge.bemerkt.model.NotesDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.note_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class NotesListAdapter internal constructor(private var context: Context, val noteDB: NotesDatabase): RecyclerView.Adapter<NotesListAdapter.ViewHolder>(){

    private var listNote = emptyList<Note>()
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    internal fun setNotes(listNote: List<Note>) {
        this.listNote = listNote
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.note_item,
            parent,
            false)
        )
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    override fun onBindViewHolder(holder: NotesListAdapter.ViewHolder, position: Int) {
        val currentNote = listNote[position]

        holder.titleView.text = currentNote.title
        holder.descriptionView.text = currentNote.desc
        //holder.deleteItemView.setOnClickListener {
        //    uiScope.launch {
        //        noteDB?.notesDao()?.delete(currentNote)
        //        listNote = noteDB?.notesDao()?.getAllNotes()
        //        notifyDataSetChanged()
        //}
        holder.editNote.setOnClickListener {
            val intent = EditActivity.createIntent(context)
            intent.putExtra("note_data", currentNote)
            context.startActivity(intent)
        }

    }


    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val titleView = view.titleTv
        val descriptionView = view.descriptionTv
        //val deleteItemView = itemView.deleteBtn
        val editNote = itemView
    }
}