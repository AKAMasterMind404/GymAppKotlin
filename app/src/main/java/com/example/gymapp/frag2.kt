package com.example.gymapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class frag2 : Fragment() {

    var counter:Int? = 0

    private val listIcons = listOf(
        R.drawable.ic1,
        R.drawable.ic2,
        R.drawable.ic3,
        R.drawable.ic4,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_frag2, container, false)

        view.findViewById<TextView>(R.id.firstName).text =
            requireArguments().getString("firstName").toString()
        view.findViewById<TextView>(R.id.lastName).text =
            requireArguments().getString("lastName").toString()
        view.findViewById<TextView>(R.id.email).text =
            requireArguments().getString("email").toString()
        view.findViewById<TextView>(R.id.height).text =
            requireArguments().getInt("height").toString()
        view.findViewById<TextView>(R.id.weight).text =
            requireArguments().getInt("weight").toString()
        view.findViewById<TextView>(R.id.calories_gained).text =
            requireArguments().getInt("caloriesBurn").toString()
        view.findViewById<TextView>(R.id.calories_burned).text =
            requireArguments().getInt("caloriesGained").toString()

        requireArguments().getString("iconIndex").toString()

        Log.d("index::", requireArguments().getString("iconIndex").toString())

        val dashboardButton = view.findViewById<Button>(R.id.dashboard_button)

        view.findViewById<ImageView>(R.id.user_icon).setImageResource(listIcons[counter!!])

        dashboardButton.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }

}