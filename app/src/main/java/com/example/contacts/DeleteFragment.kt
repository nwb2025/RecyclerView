package com.example.contacts

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class DeleteFragment : DialogFragment() {

    var onPositiveButtonClicked : () -> Unit =  {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Do you really want to delete it ?")
            .setPositiveButton("Yes") { dialog, _ ->
                onPositiveButtonClicked()
            }
            .setNegativeButton("No") { dialog , _ ->
                dialog.dismiss()
            }
            .create()

    companion object {
        fun newInstance() = DeleteFragment()
    }

}