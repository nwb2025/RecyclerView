package com.example.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.contacts.utils.Person

class ContactListFragment  : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }
    private fun initViews(view: View){
        val textViewName1 = view.findViewById<TextView>(R.id.name_person1)
        val textViewNumber1 = view.findViewById<TextView>(R.id.num_person1)
        textViewNumber1.text = MainActivity.contactsList[0].number
        textViewName1.text = MainActivity.contactsList[0].name

        val textViewName2 = view.findViewById<TextView>(R.id.name_person2)
        val textViewNumber2 = view.findViewById<TextView>(R.id.num_person2)
        textViewNumber2.text = MainActivity.contactsList[1].number
        textViewName2.text = MainActivity.contactsList[1].name

        val textViewName3 = view.findViewById<TextView>(R.id.name_person3)
        val textViewNumber3 = view.findViewById<TextView>(R.id.num_person3)
        textViewName3.text = MainActivity.contactsList[2].name
        textViewNumber3.text = MainActivity.contactsList[2].number

        val textViewName4 = view.findViewById<TextView>(R.id.name_person4)
        val textViewNumber4 = view.findViewById<TextView>(R.id.num_person4)
        textViewName4.text = MainActivity.contactsList[3].name
        textViewNumber4.text = MainActivity.contactsList[3].number
    }

}