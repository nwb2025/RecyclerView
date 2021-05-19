package com.example.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.contacts.utils.Person

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager:FragmentManager

    companion object {
        var contactsList:MutableList<Person> = mutableListOf(
                Person("Steve","123"),
                Person("Max","456"),
                Person("Alex","789"),
                Person("Robert","101112"))
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(R.id.container, ContactListFragment())
            .commit()
    }

    fun onContactClicked(view: View){
        var id = 0
        when(view?.id){
            R.id.person1 -> id = 0

            R.id.person2 -> id = 1

            R.id.person3 -> id = 2

            R.id.person4 -> id = 3
        }
        onClickedItem(id)
    }

    fun onClickedItem(id:Int){
        val fragmentContainer = findViewById<FrameLayout>(R.id.container1)
        if ( fragmentContainer != null){
            fragmentManager.beginTransaction()
                .replace(R.id.container1, DetailedContactFragment(id) { -> onRefreshFragment() })
                .commit()
        }else{
            fragmentManager.beginTransaction()
                .replace(R.id.container, DetailedContactFragment(id) { -> onRefreshFragment() })
                .addToBackStack("detailed_fragment")
                .commit()
        }
    }
    fun onRefreshFragment(){
        val fragmentContainer = findViewById<FrameLayout>(R.id.container1)
        if ( fragmentContainer != null){
            val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
            if (currentFragment is ContactListFragment ) {
                val fragTransaction = supportFragmentManager.beginTransaction()
                fragTransaction.detach(currentFragment)
                fragTransaction.attach(currentFragment)
                fragTransaction.commit()
            }
            val detailedFragment = supportFragmentManager.findFragmentById(R.id.container1)
            val fragTransaction = supportFragmentManager.beginTransaction()
            fragTransaction.detach(detailedFragment!!)
            fragTransaction.attach(detailedFragment!!)
            fragTransaction.commit()
        }
    }
}