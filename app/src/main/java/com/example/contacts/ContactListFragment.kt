package com.example.contacts

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.example.contacts.utils.Person
import com.example.contacts.utils.adapters.ContactAdapter

class ContactListFragment  : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: ContactAdapter
    private var matchedContacts:MutableList<Person> = mutableListOf()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recView)
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recyclerView.itemAnimator = DefaultItemAnimator()
        val dividerItemDecorator = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecorator.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
        recyclerView.addItemDecoration(dividerItemDecorator)

        adapter = ContactAdapter { position: Int -> showDialog(position) }
        adapter.submitList(MainActivity.contactsList)
        adapter.onItemClicked = { position ->
            val detailedContactFragment = DetailedContactFragment(adapter.currentList[position])
            detailedContactFragment.onUpdatedItem = { name, number ->
                updateItem(position, name, number)
            }
            val fragmentContainer = view?.findViewById<FrameLayout>(R.id.container1)
            if (fragmentContainer != null) {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.container1, detailedContactFragment)
                    ?.commit()
            } else {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.container, detailedContactFragment)
                    ?.addToBackStack("detailed_fragment")
                    ?.commit()
            }
        }

        recyclerView.adapter = adapter
    }

    fun showDialog(position: Int): Boolean {
        val deleteFragment = DeleteFragment.newInstance().apply {
            onPositiveButtonClicked = { deleteItem(position) }
        }
        deleteFragment.show(childFragmentManager, null)
        return true
    }

    fun deleteItem(position: Int) {
        val oldContactList = adapter.currentList.toMutableList()
        oldContactList.removeAt(position)
        adapter.submitList(oldContactList)
    }

    fun updateItem(position: Int, name: String, number: String) {
        val oldContactList = adapter.currentList.toMutableList()
        oldContactList[position].apply {
            this.name = name
            this.number = number
        }
        adapter.submitList(oldContactList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainActivity.contactsList = adapter.currentList
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.actionSearch)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if ( query != null || query != "" )
                    search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if ( newText != null || newText != "" )
                    search(newText)
                return true
            }

        })
    }

    private fun search(text: String?){
        matchedContacts = mutableListOf<Person>()
        text?.let {
            adapter.currentList.forEach{ person ->
                if ( person.name.toLowerCase().contains(text, true))
                    matchedContacts.add(person)
            }
        }
        updateRecView()
        if ( matchedContacts.isEmpty()){
            Toast.makeText(context, "No match found!", Toast.LENGTH_SHORT).show()
        }
        updateRecView()
    }

    private fun updateRecView(){
        adapter.submitList(matchedContacts)
    }

}