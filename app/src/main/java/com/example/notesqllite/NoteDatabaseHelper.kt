package com.example.notesqllite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class NoteDatabaseHelper(context: Context)
    : SQLiteOpenHelper(context , DATABASE_NAME ,null , DATABASE_VERSION)
{
    companion object{
        const val DATABASE_NAME = "NOTES.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "NOTE_DATA"
        const val COLUMN_ID = "Id"
        const val COLUMN_TITLE = "Title"
        const val COLUMN_CONTENT = "Content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY , $COLUMN_TITLE TEXT , $COLUMN_CONTENT TEXT )"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun insertNote(note : NoteData){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE , note.title)
            put(COLUMN_CONTENT , note.content)
        }
        db.insert(TABLE_NAME , null , values)
        close()
    }

    fun getAllNotes():List<NoteData>{

        val  notesList = mutableListOf<NoteData>()
        val query = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        val cursor = db.rawQuery(query,null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

                val note =NoteData(id ,title , content)
                notesList.add(note)

        }

        cursor.close()
        db.close()
        return notesList


    }


    fun updateNote(note: NoteData){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE , note.title)
            put(COLUMN_CONTENT , note.content)
        }
        val whereClause  = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())

        db.update(TABLE_NAME , values , whereClause ,whereArgs)
        db.close()
    }

    fun getNoteById(noteId:Int):NoteData{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id= cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val  title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val  content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()

        return NoteData(id, title, content)



    }

    fun deleteNote(noteID:Int){
       val db =writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteID.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()

    }

}