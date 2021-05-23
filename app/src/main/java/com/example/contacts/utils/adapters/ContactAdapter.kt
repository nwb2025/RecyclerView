package com.example.contacts.utils.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.utils.Person

class ContactAdapter ( val onLongItemClicked : (Int) -> Boolean) : ListAdapter<Person,
        ContactAdapter.PersonViewHolder>(ContactsDiffCallback()) {
    var onItemClicked: (Int) -> Unit =  {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder  {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item,parent,false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder , position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{
            onItemClicked(holder.adapterPosition)
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClicked(holder.adapterPosition)
        }

    }

    class PersonViewHolder ( itemVew: View) : RecyclerView.ViewHolder(itemVew) {
        private val name = itemVew.findViewById<TextView>(R.id.name_person)
        private val number = itemVew.findViewById<TextView>(R.id.num_person)

        fun bind(person:Person){
            name.text = person.name
            number.text = person.number
        }
    }

}

// used to figure out how the elements have changed
class ContactsDiffCallback : DiffUtil.ItemCallback<Person>(){
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean  = oldItem == newItem

}