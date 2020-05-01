package com.example.notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_list.*

class NoteListActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_list)



        val toolbar = toolbar as Toolbar
        setSupportActionBar(toolbar)

        notes = mutableListOf<Note>()

        // jeu de tests
        notes.add(Note("Refractor méthode 1", " Praesent condimentum lacinia augue,lectus at mi sodales, ac fermentum urna porttitor."))
        notes.add(Note("Faire un commit", "Cras blandit lectus at mi sodales, ac fermentum urna porttitor. Praesent condimentum lacinia augue,"))
        notes.add(Note("Faire un push", " Praesent condimentum lacinia augue,"))

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

    override fun onClick(view: View) {
        if (view.tag != null) {
            Log.i("NoteListActivity", "Click !")
            showNoteDetail(view.tag as Int)
        }
    }

    fun showNoteDetail(noteIndex : Int){
        val note = notes[noteIndex]

        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)

        startActivity(intent)

    }
}
