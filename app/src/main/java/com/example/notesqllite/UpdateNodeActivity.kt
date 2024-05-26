package com.example.notesqllite


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesqllite.databinding.ActivityUpdateNoteBinding

class UpdateNodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db:NoteDatabaseHelper
    private var  noteId :Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db= NoteDatabaseHelper(this)
        noteId= intent.getIntExtra("note_id" , -1)
        if (noteId == -1){
            finish()
            return
        }
        val oldNote =db.getNoteById(noteId)

        binding.updateTitleEditText.setText(oldNote.title)
        binding.updateContentEditText.setText(oldNote.content)

        binding.SaveUpdateButton.setOnClickListener {
            val title = binding.updateTitleEditText.text.toString()
            val content = binding.updateContentEditText.text.toString()
            if (title.isNotEmpty() && content.isNotEmpty()){
                val newNote =NoteData(noteId ,title ,content)
                db.updateNote(newNote)
                finish()
                Toast.makeText(this ,"  Note Updated  ", Toast.LENGTH_SHORT).show()
            }else
                Toast.makeText(this,"Fill Your Title And Content First",Toast.LENGTH_SHORT).show()

        }

    }
}