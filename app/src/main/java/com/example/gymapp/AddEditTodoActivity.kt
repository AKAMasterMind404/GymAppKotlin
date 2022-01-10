package com.example.gymapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class AddEditTodoActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var todoTitleEdt: EditText
    lateinit var todoDescriptionEdt: EditText
    lateinit var addupdateBtn: Button
    lateinit var viewModel: TodoViewModel
    var todoID = -1

    // USER INITIALIZATION
    var email: String = ""
    var id: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var iconIndex: String = ""
    var username: String = ""

    var age: Int = 0
    var height: Int = 0
    var weight: Int = 0

    var caloriesBurn = 0.0
    var caloriesGained = 0.0
    // USER INITIALIZATION END

    var currUser:User = User(
        id = id,
        caloriesBurn = caloriesBurn,
        caloriesGained = caloriesGained,
        firstName = firstName,
        lastName = lastName,
        username = username,
        weight = weight,
        height = height,
        age = age,
        iconIndex = iconIndex
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_todo)

        auth = FirebaseAuth.getInstance()

        todoTitleEdt = findViewById(R.id.idEdtTodoTitle)
        todoDescriptionEdt = findViewById(R.id.idEdtTodoDescription)
        addupdateBtn = findViewById(R.id.idBtnAddUpdate)
        val userTF: EditText = findViewById(R.id.user_input_calories)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(TodoViewModel::class.java)

        val todoType = intent.getStringExtra("todoType")
        if (todoType.equals("Edit")) {
            val todoTitle = intent.getStringExtra("todoTitle")
            val todoDes = intent.getStringExtra("todoDescription")
            todoID = intent.getIntExtra("noteID", -1)

            addupdateBtn.setText("Update Todo")
            todoTitleEdt.setText(todoTitle)
            todoDescriptionEdt.setText(todoDes)
        } else {
            addupdateBtn.setText("Add Todo")
        }

        FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid)
            .get().addOnSuccessListener {
                it.children.forEach {
                    if (it.key == "caloriesGained") currUser.caloriesGained = currUser.caloriesGained?.plus(
                        it.value.toString().toDouble()
                    )
                    if (it.key == "caloriesBurn") currUser.caloriesBurn = currUser.caloriesBurn?.plus(
                        it.value.toString().toDouble()
                    )

                    Log.d("hi:",it.toString())

                    if (it.key == "age") currUser.age = it.value.toString().toInt()
                    if (it.key == "height") currUser.height = it.value.toString().toInt()
                    if (it.key == "weight") currUser.weight = it.value.toString().toInt()

                    if (it.key == "email") currUser.email = it.value.toString()
                    if (it.key == "id") currUser.id = it.value.toString()
                    if (it.key == "firstName") currUser.firstName = it.value.toString()
                    if (it.key == "lastName") currUser.lastName = it.value.toString()
                    if (it.key == "iconIndex") currUser.iconIndex = it.value.toString()
                    if (it.key == "username") currUser.username = it.value.toString()
                }
            }

        addupdateBtn.setOnClickListener {
            val todoTitle = todoTitleEdt.text.toString()
            val todoDescription = todoDescriptionEdt.text.toString()

            Log.d("ho::","-----------")
            Log.d("ho::",currUser.email.toString())
            Log.d("ho::",currUser.username.toString())
            Log.d("ho::",currUser.firstName.toString())
            Log.d("ho::",currUser.lastName.toString())
            Log.d("ho::",currUser.weight.toString())
            Log.d("ho::",currUser.height.toString())

            if (userTF.text.isEmpty()) {
                Toast.makeText(this, "Enter calorie value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val calEntered = userTF.text.toString().toDouble()

            val id = findViewById<RadioGroup>(R.id.radio_group_1).checkedRadioButtonId

            if(id.equals(R.id.gained)){
                currUser.caloriesGained = currUser.caloriesGained?.plus(calEntered)
            }
            else if(id.equals(R.id.lost)) {
                currUser.caloriesBurn = currUser.caloriesBurn?.plus(calEntered)
            }
            if (todoType.equals("Edit")) {
                if (todoTitle.isNotEmpty() && todoDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updateTodo = Todo(todoTitle, todoDescription, currentDate)
                    updateTodo.id = todoID
                    viewModel.updateTodo(updateTodo)

                    Toast.makeText(this, "Todo updated!", Toast.LENGTH_LONG).show()
                }
            } else {
                if (todoTitle.isNotEmpty() && todoDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    viewModel.addTodo(Todo(todoTitle, todoDescription, currentDate))
                    Toast.makeText(this, "Todo added!", Toast.LENGTH_LONG).show()
                }
            }

            FirebaseDatabase.getInstance().getReference("users")
                .child(auth.currentUser!!.uid).setValue(currUser)
                .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Success", "Updated Calorie Count")
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {

                }
            }
        }
    }
}