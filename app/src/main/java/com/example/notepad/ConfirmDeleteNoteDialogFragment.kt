package com.example.notepad

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmDeleteNoteDialogFragment(val noteTitle : String? = ""): DialogFragment() {

    interface ConfirmDeleteDialogListener {
        fun onDialogPositivClick()
        fun onDialogNegativClick()
    }

    var listener: ConfirmDeleteDialogListener?  = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        //configuration
        builder.setMessage("Êtes-vous sûr de vouloir supprimer la note \"$noteTitle\" ?")
                .setPositiveButton("Supprimer", DialogInterface.OnClickListener { dialog, id -> listener?.onDialogPositivClick()  })
                .setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, id -> listener?.onDialogNegativClick() })

        return builder.create()
    }
}