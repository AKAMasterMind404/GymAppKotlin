package com.example.gymapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class frag1 : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_frag1, container, false)
        val iconIndex:Int = 0
        val profileButton = view.findViewById<RelativeLayout>(R.id.profile_1_bar)
        val settingsButton = view.findViewById<RelativeLayout>(R.id.settings2_bar)
        val signOutButton = view.findViewById<RelativeLayout>(R.id.signOut3_bar)

        profileButton.setOnClickListener {
            val mDatabase = FirebaseDatabase.getInstance()
            val databaseReference = mDatabase.getReference("users")

            databaseReference.get()
                .addOnSuccessListener {
                    Log.i("firebase", it.children.toString())

                    it.children.forEach {
                        val id = it.child("id").value
                        val age = it.child("age").value
                        val email = it.child("email").value
                        val firstName = it.child("firstName").value
                        val lastName = it.child("lastName").value
                        val userName = it.child("username").value
                        val weight = it.child("weight").value
                        val iconIndex = it.child("iconIndex").value

                        if(auth.currentUser!!.uid == iconIndex){
                            Log.i("hi","Hello")
                        }

                    }
                    findNavController().navigate(R.id.action_frag1_to_frag2).apply {
                    // TODO: PASS VARIABLES BETWEEN FRAGMENTS
                    }
                }
        }

        auth = FirebaseAuth.getInstance()

        signOutButton.setOnClickListener {
            auth.signOut()
            Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(Intent(context, MainActivity::class.java))
        }

        return view
    }

}