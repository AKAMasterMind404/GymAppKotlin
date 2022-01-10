package com.example.gymapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.core.widget.TextViewOnReceiveContentListener
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailText: EditText
    private lateinit var nameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var iconView: ImageView

    var counter = 0

    private val listIcons = listOf(
        R.drawable.ic1,
        R.drawable.ic2,
        R.drawable.ic3,
        R.drawable.ic4,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        emailText = findViewById(R.id.etRegisterEmail)
        nameText = findViewById(R.id.etName)
        passwordText = findViewById(R.id.etRegisterPassword)
        iconView = findViewById(R.id.register_userIcon)

        val loginButton: TextView = findViewById(R.id.btLogin)
        val nextButton: Button = findViewById(R.id.btNext)

        loginButton.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        iconView.setOnClickListener {
            if(counter>=listIcons.size-1){
                counter = 0
            }
            else{
                counter++
            }
            iconView.setImageResource(listIcons[counter])
        }

        nextButton.setOnClickListener{
            signUpUser()
        }

    }

    private fun signUpUser() {
        if (nameText.text.toString().isEmpty()){
            nameText.error = "Please enter name"
            nameText.requestFocus()
            return
        }
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
            auth.createUserWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString()).addOnCompleteListener(this){
                    task ->
                if (task.isSuccessful){
                    Log.d("Success", "created user")
                    Intent(this, UserDetailsActivity::class.java).also {
                        it.putExtra("username", nameText.text.toString())
                        it.putExtra("email", emailText.text.toString())
                        it.putExtra("iconIndex", counter.toString())

                        startActivity(it)
                    }
                } else{
                    Toast.makeText(baseContext, "Sign Up Failed.", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }

    }
}