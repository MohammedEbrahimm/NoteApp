package com.example.notesqllite

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private var notes:List<NoteData> , context: Context):
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>()
{
    private val db = NoteDatabaseHelper(context)
    class NoteViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val titleTextView :TextView = itemView.findViewById(R.id.TitleTextView)
        val contentTextView :TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton:ImageView = itemView.findViewById(R.id.updateButtonImageView)
        val deleteButton:ImageView = itemView.findViewById(R.id.deleteButtonImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // setup the item layout view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return NoteViewHolder(view)

    }

    override fun getItemCount(): Int  = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        // we set data on the element
        // here we have holder and position as their parameters so holder basically help to set data and position helps to determine which item was clicked
        val note =notes[position]
            holder.titleTextView.text = note.title
            holder.contentTextView.text = note.content
            holder.updateButton.setOnClickListener {

           val intent = Intent(holder.itemView.context , UpdateNodeActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)

        }

        holder.deleteButton.setOnClickListener {

            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context ," Note Deleted " , Toast.LENGTH_SHORT).show()
        }






    }
    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newNote :List<NoteData>){
        notes = newNote
        notifyDataSetChanged()
    }
}