package com.example.gymapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymapp.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    lateinit var addFAB: FloatingActionButton
    private lateinit var auth: FirebaseAuth

    lateinit var homeFragment: homeFragment
    lateinit var profileFragment: profileFragment
    lateinit var settingsFragment: settingsFragment

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNavMenu)
        var frame: FrameLayout = findViewById(R.id.frame_layout)

        homeFragment = homeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomNav.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.home->{
                    homeFragment = homeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.settings->{
                    settingsFragment = settingsFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, settingsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.profile->{
                    profileFragment = profileFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, profileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
        }

        val logoutButton: Button = findViewById(R.id.btLogout)
        logoutButton.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
        addFAB = findViewById(R.id.idFABAddTodo)

        addFAB.setOnClickListener{
            val intent = Intent(this@HomeActivity, AddEditTodoActivity::class.java)
            startActivity(intent)
        }

    }

}
//{
//
//    private lateinit var auth: FirebaseAuth
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//
//        auth = FirebaseAuth.getInstance()
//
//        val logoutButton: Button = findViewById(R.id.btLogout)
//        logoutButton.setOnClickListener(){
//            auth.signOut()
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//    }
//}