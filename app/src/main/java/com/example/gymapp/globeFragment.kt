package com.example.gymapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase

class globeFragment : Fragment() {

    private lateinit var globalRecyler: RecyclerView
    private var user_global_list: MutableList<User> = mutableListOf(
//    User(iconIndex = "1", username = "Username1"),
//    User(iconIndex = "2", username = "Username2"),
//    User(iconIndex = "3", username = "Username3"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_globe, container, false)

        val globeAdapter = GlobalUserAdapter(
            requireActivity().applicationContext,
            user_global_list
        )

        globalRecyler = view.findViewById(R.id.globalRecycler)
        globalRecyler.layoutManager = LinearLayoutManager(context)
        globalRecyler.adapter = globeAdapter

        val localList: MutableList<User> = mutableListOf()

        FirebaseDatabase.getInstance().getReference("users").get().addOnSuccessListener {
            it.children.forEach {
                Log.i("ow::", it.child("iconIndex").value.toString())
                Log.i("ow::", it.child("username").value.toString())
                localList.add(
                    User(
                        iconIndex = it.child("iconIndex").value.toString(),
                        username = it.child("username").value.toString()
                    )
                )
            }

            user_global_list.addAll(localList)
            globeAdapter.notifyDataSetChanged()
        }

        view.findViewById<FloatingActionButton>(R.id.gloablFAB)
            .setOnClickListener {
                findNavController().popBackStack()
//                R.id.action_globeFragment_to_frag1
            }

        return view
    }

}