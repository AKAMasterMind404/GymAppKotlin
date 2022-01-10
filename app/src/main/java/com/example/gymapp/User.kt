package com.example.gymapp

data class User(
    var id: String? = null,
    var username: String? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var height: Int? = null,
    var weight: Int? = null,
    var age: Int? = null,
    var caloriesGained: Double? = 0.0,
    var caloriesBurn: Double? = 0.0,
    var iconIndex:String? = "0"
)