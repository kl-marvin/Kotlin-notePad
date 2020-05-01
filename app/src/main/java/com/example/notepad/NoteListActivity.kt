package com.example.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NoteListActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_list)

        notes = mutableListOf<Note>()

        notes.add(Note("Refractor méthode 1", " Praesent condimentum lacinia augue,lectus at mi sodales, ac fermentum urna porttitor."))
        notes.add(Note("Faire un commit", "Cras blandit lectus at mi sodales, ac fermentum urna porttitor. Praesent condimentum lacinia augue,"))
        notes.add(Note("Faire un push", " Praesent condimentum lacinia augue,"))


        adapter = NoteAdapter(notes, this)

        val recyclerView = findViewById(R.id.notes_recycler_view) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    override fun onClick(view: View) {
        if (view.tag != null){
            Log.i("NoteListActivity", "Click !")
        }

    }
}
