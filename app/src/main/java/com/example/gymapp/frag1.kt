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
        val iconIndex: Int = 0
        val profileButton = view.findViewById<RelativeLayout>(R.id.profile_1_bar)
        val globeButton = view.findViewById<RelativeLayout>(R.id.globeUsers2_bar)
        val signOutButton = view.findViewById<RelativeLayout>(R.id.signOut3_bar)

        val mDatabase = FirebaseDatabase.getInstance()
        val databaseReference = mDatabase.getReference("users")
        val data = Bundle()

        profileButton.setOnClickListener {

            databaseReference.get()
                .addOnSuccessListener {
                    Log.i("firebase", it.children.toString())

                    it.children.forEach {
                        val id = it.child("id").value.toString()
                        val age = it.child("age").value.toString().toIntOrNull()
                        val email = it.child("email").value.toString()
                        val firstName = it.child("firstName").value.toString()
                        val lastName = it.child("lastName").value.toString()
                        val userName = it.child("username").value.toString()
                        val weight = it.child("weight").value.toString().toIntOrNull()
                        val height = it.child("height").value.toString().toIntOrNull()
                        val iconIndex = it.child("iconIndex").value.toString()
                        val caloriesBurn =
                            it.child("caloriesBurn").value.toString().toDoubleOrNull()
                        val caloriesGained =
                            it.child("caloriesGained").value.toString().toDoubleOrNull()

                        if (auth.currentUser!!.uid == id) {
                            data.putString("email",email)
                            data.putString("firstName",firstName)
                            data.putString("lastName",lastName)
                            data.putString("userName",userName)
                            data.putString("iconIndex",iconIndex)
                            data.putString(id,id)

                            Log.i("Result::", iconIndex)

                            if (age != null) {
                                data.putInt("age",age)
                            }

                            if (weight != null) {
                                data.putInt("weight",weight)
                            }

                            if (height != null) {
                                data.putInt("height",height)
                            }

                            if (caloriesBurn != null) {
                                data.putDouble("caloriesBurn",caloriesBurn)
                            }

                            if (caloriesGained != null) {
                                data.putDouble("caloriesGained",caloriesGained)
                            }

                            Log.i("hi", "Hello")
                        }

                    }
                    findNavController().navigate(R.id.action_frag1_to_frag2, data)
                }
        }

        auth = FirebaseAuth.getInstance()

        globeButton.setOnClickListener {
            databaseReference.get().addOnSuccessListener {
                findNavController().navigate(R.id.action_frag1_to_globeFragment, data)
            }
        }

        signOutButton.setOnClickListener {
            auth.signOut()
            Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(Intent(context, MainActivity::class.java))
        }

        return view
    }

}