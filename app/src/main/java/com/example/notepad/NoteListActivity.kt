package com.example.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.utils.loadNotes
import com.example.notepad.utils.persistNote
import kotlinx.android.synthetic.main.note_list.*

class NoteListActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_list)



        val toolbar = toolbar as Toolbar
        setSupportActionBar(toolbar)

        create_note_fab.setOnClickListener(this)

        notes = loadNotes(this)


        // on crée l'adapteur en lui passant les données
        adapter = NoteAdapter(notes, this)

        // On met en relation l'adapter et le RecyclerView
        // récupération
        val recyclerView = findViewById(R.id.notes_recycler_view) as RecyclerView
        // configuration
        recyclerView.layoutManager = LinearLayoutManager(this)
        // association
        recyclerView.adapter = adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // si il n'y a pas de traitement à faire ...
        if(resultCode != Activity.RESULT_OK || data == null){
            return
        }
        when (requestCode){
            NoteDetailActivity.REQUEST_EDIT_NOTE -> processEditNoteResult(data)
        }

    }

    private fun processEditNoteResult(data: Intent) {
            val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)

        when (data.action) {
            NoteDetailActivity.ACTION_SAVE_NOTE -> {
                val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
                saveNote(note, noteIndex)
            }
            NoteDetailActivity.ACTION_DELETE_NOTE -> {
                deleteNote(noteIndex)
            }
        }
    }


    override fun onClick(view: View) {
        if (view.tag != null) {
            Log.i("NoteListActivity", "Click !")
            showNoteDetail(view.tag as Int)
        }else{
            when(view.id){
                R.id.create_note_fab -> createNewNote()
            }
        }
    }

    private fun createNewNote() {
       showNoteDetail(-1)

    }


    fun saveNote(note: Note, noteIndex: Int){
        com.example.notepad.utils.persistNote(this, note)

        if( noteIndex < 0){
            // on ajoute la nouvelle note à l'index 0 du tableau
            notes.add(0, note)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Note ajoutée", Toast.LENGTH_LONG).show()

        }else{
            notes[noteIndex] = note
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Note modifiée", Toast.LENGTH_LONG).show()

        }
    }

    fun deleteNote(noteIndex: Int){
        if(noteIndex < 0){
            return
        }
        val note = notes.removeAt(noteIndex)
        com.example.notepad.utils.deleteNote(this, note)
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Note supprimée", Toast.LENGTH_LONG).show()
    }

    fun showNoteDetail(noteIndex : Int){
        // si l'index n'est pas dans les bornes du tableau on crée un nouvelle note
        val note = if (noteIndex < 0) Note() else notes[noteIndex]

        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note as Parcelable)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)

        startActivityForResult(intent, NoteDetailActivity.REQUEST_EDIT_NOTE)

    }
}
