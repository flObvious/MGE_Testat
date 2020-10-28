package ch.ost.rj.mge.bemerkt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.ost.rj.mge.bemerkt.model.Note
import ch.ost.rj.mge.bemerkt.model.NotesDatabase
import kotlinx.android.synthetic.main.note_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class NotesListAdapter internal constructor(context: Context, val noteDB: NotesDatabase): RecyclerView.Adapter<NotesListAdapter.ViewHolder>(){

    private var listNote = emptyList<Note>()
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    internal fun setNotes(listNote: List<Note>) {
        this.listNote = listNote
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(itemView)
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
        //    }
        //}
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val titleView = itemView.titleTv
        val descriptionView = itemView.descriptionTv
        //val deleteItemView = itemView.deleteBtn
    }
}