package ch.ost.rj.mge.bemerkt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.ost.rj.mge.bemerkt.model.Note
import ch.ost.rj.mge.bemerkt.model.NotesDatabase
import ch.ost.rj.mge.bemerkt.view.EditActivity
import kotlinx.android.synthetic.main.note_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NotesListAdapter internal constructor(private var context: Context, private val noteDB: NotesDatabase): RecyclerView.Adapter<NotesListAdapter.ViewHolder>(){

    private var noteList = emptyList<Note>()
    private val job = Job()
    private val mainScope = CoroutineScope(Dispatchers.Main + job)

    internal fun setNotes(listNote: List<Note>) {
        this.noteList = listNote
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
        return noteList.size
    }

    override fun onBindViewHolder(holder: NotesListAdapter.ViewHolder, position: Int) {
        val currentNote = noteList[position]

        holder.titleView.text = currentNote.title
        holder.descriptionView.text = currentNote.desc
        holder.editNote.setOnClickListener {
            val intent = EditActivity.createIntent(context)
            intent.putExtra("note_data", currentNote)
            context.startActivity(intent)
        }

    }

    fun deleteItem(position: Int){
        mainScope.launch{
            noteDB.noteDao().delete(noteList[position])
            noteList = noteDB.noteDao().getAllNotes()
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val titleView: TextView = view.item_title
        val descriptionView: TextView = view.item_description
        val editNote = itemView
    }
}