package com.example.gymapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this,"Settings Selected",Toast.LENGTH_SHORT).show()

        when (item.itemId){
            R.id.settings -> {
                Toast.makeText(this,"Settings Selected",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
//        updateUI(currentUser)
    }

//    fun updateUI(currentUser: FirebaseUser?){
//
//    }
}