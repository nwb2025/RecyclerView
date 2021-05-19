package com.example.contacts

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import org.w3c.dom.Text

class EditContactFragment (val uid:Int) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setTitle("Edit Contact")
        return inflater.inflate(R.layout.fragment_edit_contact, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }
    private fun initViews(view: View){
        val editContact = view.findViewById<ConstraintLayout>(R.id.edit_contact)
        val editTextNumber = view.findViewById<EditText>(R.id.tv_num2)
        val editTextName = view.findViewById<EditText>(R.id.tv_name2)
        val textViewName: TextView = view.findViewById(R.id.tv_name2)
        val textViewNumber:TextView = view.findViewById(R.id.tv_num2)
        textViewName.text = MainActivity.contactsList[uid].name
        textViewNumber.text = MainActivity.contactsList[uid].number

        editContact.setOnClickListener{
            when {
                editTextNumber.text.toString() == "" -> editTextNumber.error = "This field can not be empty"
                editTextName.text.toString() == "" -> editTextName.error = "This field can not be empty"
                else -> {
                    val intent = Intent()
                    intent.putExtra("name",editTextName.text.toString())
                    intent.putExtra("number", editTextNumber.text.toString())
                    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                    dismiss()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

}