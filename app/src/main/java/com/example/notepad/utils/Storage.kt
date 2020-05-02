package com.example.notepad.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.example.notepad.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

/*********************
 * TODO add try/catch
 *********************/

private val TAG = "storage"
// Gestion de la persistance

fun persistNote(context: Context, note: Note){

    // si la note n'a pas de titre (nouvelle note) alors on en crée un aléatoire fichier.note
    if(TextUtils.isEmpty(note.fileName)){
        note.fileName = UUID.randomUUID().toString() + ".note"
    }


    // On ouvre un fichier restraint à l'appli
    val fileOutPut = context.openFileOutput(note.fileName, Context.MODE_PRIVATE)

    Log.i(TAG, "Saving note $note")

    // Ouverture du flux de données et ajout du notre fichier crée
   val outputStream = ObjectOutputStream(fileOutPut)
    // Ecriture dans le fichier
    outputStream.writeObject(note)
    outputStream.close()
}

fun loadNotes(context: Context): MutableList<Note>{

    val notes = mutableListOf<Note>()
    // on récupère le dossier
    val notesDir = context.filesDir

    // on boucle dessus pour récupérer les notes
    for(filename in notesDir.list()){
        val note = loadNote(context, filename)
        Log.i(TAG, "Loaded note $note")
        // on ajoute la note à la liste
        notes.add(note)
    }

    return notes
}

private fun loadNote(context: Context, filename: String) : Note{
    // on ouvre le fichier en mode lecture
    val fileInput = context.openFileInput(filename)
    // on ouvre le flux pour lire dans le ficher
    val inputStream = ObjectInputStream(fileInput)
    // on lie la note avec readObject
    val note = inputStream.readObject() as Note
    inputStream.close()

    return note
}

fun deleteNote(context: Context, note: Note){
    context.deleteFile(note.fileName)
}