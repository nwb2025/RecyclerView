package com.example.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import com.example.contacts.utils.Person
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager:FragmentManager

    companion object {
        var contactsList = mutableListOf<Person>()
    }
    init {
        repeat(100){
            contactsList.add(
                Person(UUID.randomUUID().toString(),
                (System.currentTimeMillis()/3600).toString(),
                    "https://picsum.photos/${it}"
            ))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(R.id.container, ContactListFragment())
            .commit()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }



}