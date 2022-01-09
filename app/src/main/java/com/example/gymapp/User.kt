package com.example.gymapp

data class User(
    val id: String? = null,
    val username: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val age: Int? = null,
    val caloriesGained: Double? = 0.0,
    val caloriesBurn: Double? = 0.0,
)