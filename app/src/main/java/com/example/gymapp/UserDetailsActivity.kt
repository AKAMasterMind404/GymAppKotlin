package com.example.gymapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firstNameText: EditText
    private lateinit var lastNameText: EditText
    private lateinit var heightText: EditText
    private lateinit var weightText: EditText
    private lateinit var ageText: EditText
    private var iconIndex: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("users")

        val addUserDataButton: Button = findViewById(R.id.btAddUserData)

        auth = FirebaseAuth.getInstance()
        firstNameText = findViewById(R.id.etFirstName)
        lastNameText = findViewById(R.id.etLastName)
        heightText = findViewById(R.id.etHeight)
        weightText = findViewById(R.id.etWeight)
        ageText = findViewById(R.id.etAge)


        addUserDataButton.setOnClickListener() {
            addUserData()
        }

    }

    private fun addUserData() {
        val username = intent.getStringExtra("username")
        val email = intent.getStringExtra("email")
        iconIndex = intent.getStringExtra("iconIndex").toString()

        if (firstNameText.text.toString().isEmpty()) {
            firstNameText.error = "Please enter valid First name"
            firstNameText.requestFocus()
            return
        }
        if (lastNameText.text.toString().isEmpty()) {
            lastNameText.error = "Please enter valid Last name"
            lastNameText.requestFocus()
            return
        }
        if (heightText.text.toString()
                .isEmpty() || Integer.parseInt(heightText.text.toString()) <= 0
        ) {
            heightText.error = "Please enter valid Height"
            heightText.requestFocus()
            return
        }
        if (weightText.text.toString()
                .isEmpty() || Integer.parseInt(weightText.text.toString()) <= 0
        ) {
            weightText.error = "Please enter valid Weight"
            weightText.requestFocus()
            return
        }
        if (ageText.text.toString().isEmpty() || Integer.parseInt(ageText.text.toString()) <= 0) {
            ageText.error = "Please enter valid Age"
            ageText.requestFocus()
            return
        }
        val user = User(
            auth.currentUser!!.uid,
            username,
            email,
            firstNameText.text.toString(),
            lastNameText.text.toString(),
            Integer.parseInt(heightText.text.toString()),
            Integer.parseInt(weightText.text.toString()),
            Integer.parseInt(ageText.text.toString()),
            0.0,
            0.0,
            iconIndex
        )
        try {
            databaseReference.push().setValue(user).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Success", "created user")
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Sign Up Failed.", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {

            Log.d("Error", e.toString())
        }
    }
}