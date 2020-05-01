package com.example.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_note_detail.*

class NoteDetailActivity : AppCompatActivity() {

    companion object {
        val REQUEST_EDIT_NOTE = 1
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"
    }

    lateinit var note: Note
    var noteIndex: Int = -1

    lateinit var textView: TextView
    lateinit var titleView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val toolbar = toolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Parcelable = Séréalisation
        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)!!
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        titleView = titleNote as TextView
        textView = text as TextView

        titleView.text = note.title
        textView.text = note.text

    }

    fun saveNote(){
        // on met à jour le modèle
        note.title = titleView.text.toString()
        note.text = textView.text.toString()

        // on prépare l'intent à envoyer
        intent = Intent()
        intent.putExtra(EXTRA_NOTE, note)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)

        // si tout va bien on renvoie result ok
        setResult(Activity.RESULT_OK, intent)
        finish()

    }


    /***********************************************************
     *** Gestion du Menu Save (toolbar activity note detail) ***
     ***********************************************************/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save -> {
                Log.i("Info  debug: ", "Save btn clicked")
                saveNote()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
