package com.example.contacts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.contacts.utils.Person

class DetailedContactFragment(
    val person: Person
) : Fragment() {

    private  val REQUEST_ONE = 1
    var onUpdatedItem : (String,String) -> Unit = { _,_ -> }


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
        val contactImage:ImageView = view.findViewById(R.id.person_img)

        textViewName.text = person.name
        number.text = person.number
        Glide.with(view.context)
            .load(person.avatarUrl)
            .error(R.mipmap.ic_launcher_round)
            .into(contactImage)

        editConstraintLayout.setOnClickListener{
            val fragment = EditContactFragment(person)
            fragment.setTargetFragment(this,REQUEST_ONE)
            fragment.show(fragmentManager!!,"editDialog")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_ONE -> {
                   onUpdatedItem(
                        data?.getStringExtra("name")!!,
                        data?.getStringExtra("number")!!)
                }
            }
        }
        activity?.supportFragmentManager?.popBackStack()
    }
}