package com.example.gymapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val registerButton: TextView = findViewById(R.id.btrRegister)
        auth = FirebaseAuth.getInstance()
        emailText = findViewById(R.id.etEmail)
        passwordText = findViewById(R.id.etPassword)

        val loginButton: Button = findViewById(R.id.loginButton)

        loginButton.setOnClickListener(){
            signInUser()
        }

        registerButton.setOnClickListener() {
            Intent(this, RegisterActivity::class.java).also {
                it.putExtra("atharv", "yeda")
                startActivity(it)
                finish()
            }
        }
    }

    private fun signInUser() {

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText.text.toString()).matches()){
            emailText.error = "Please enter a valid email"
            emailText.requestFocus()
            return
        }
        if (passwordText.text.toString().isEmpty()){
            passwordText.error = "Please enter password"
            passwordText.requestFocus()
            return
        }
        try {
            auth.signInWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString()).addOnCompleteListener(this){
                    task ->
                if (task.isSuccessful){
                    Log.d("Success", "created user")
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else{
                    Toast.makeText(baseContext, "Sign In Failed.", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }

    }
}