package com.example.gymapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class globeFragment : Fragment() {

    private lateinit var globalRecyler:RecyclerView
    private var user_global_list: MutableList<User> = mutableListOf(
    User(iconIndex = "1", username = "Username1"),
    User(iconIndex = "2", username = "Username2"),
    User(iconIndex = "3", username = "Username3"),
    )
    // registerCalBurn
    // getCalBurn

    // registerCalGain
    // getCalGain

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

        view.findViewById<FloatingActionButton>(R.id.gloablFAB)
            .setOnClickListener {
                findNavController().navigate(R.id.action_globeFragment_to_frag1)
            }

        return view
    }

}