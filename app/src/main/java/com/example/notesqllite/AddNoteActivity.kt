package com.example.notesqllite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesqllite.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db:NoteDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = NoteDatabaseHelper(this)

        binding.SaveButton.setOnClickListener{

            val title = binding.TitleEditText.text.toString()
            val content = binding.ContentEditText.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()){
                val note = NoteData(0 , title , content)
                db.insertNote(note)
                finish()
                Toast.makeText(this ,"  Note Saved  ",Toast.LENGTH_SHORT).show()
            }else
                Toast.makeText(this,"Fill Your Title And Content First",Toast.LENGTH_SHORT).show()

        }
    }
}