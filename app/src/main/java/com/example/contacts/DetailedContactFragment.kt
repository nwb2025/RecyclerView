package com.example.contacts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


class DetailedContactFragment(
    val uid: Int,
    private val onRefreshFragment: () -> Unit
) : Fragment() {

    private  val REQUEST_ONE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detailed_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }
    private fun initViews(view: View){
        val textViewName:TextView = view.findViewById(R.id.name)
        val number:TextView = view.findViewById(R.id.tv_num2)
        val editConstraintLayout:ConstraintLayout = view.findViewById(R.id.edit_contact)

        textViewName.text = MainActivity.contactsList[uid].name
        number.text = MainActivity.contactsList[uid].number

        editConstraintLayout.setOnClickListener{
            val fragment = EditContactFragment(uid)
            fragment.setTargetFragment(this,REQUEST_ONE)
            fragment.show(fragmentManager!!,"editDialog")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_ONE -> {
                    MainActivity.contactsList[uid].name = data?.getStringExtra("name")!!
                    MainActivity.contactsList[uid].number = data?.getStringExtra("number")!!
                }
            }
        }
        activity?.supportFragmentManager?.popBackStack()
        onRefreshFragment()
    }



}