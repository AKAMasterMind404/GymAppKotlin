package com.example.gymapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditTodoActivity : AppCompatActivity() {
    lateinit var todoTitleEdt: EditText
    lateinit var todoDescriptionEdt: EditText
    lateinit var addupdateBtn: Button
    lateinit var viewModel: TodoViewModel
    var todoID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_todo)

        todoTitleEdt = findViewById(R.id.idEdtTodoTitle)
        todoDescriptionEdt = findViewById(R.id.idEdtTodoDescription)
        addupdateBtn = findViewById(R.id.idBtnAddUpdate)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TodoViewModel::class.java)

        val todoType = intent.getStringExtra("todoType")
        if (todoType.equals("Edit")){
            val todoTitle = intent.getStringExtra("todoTitle")
            val todoDes = intent.getStringExtra("todoDescription")
            todoID = intent.getIntExtra("noteID", -1)

            addupdateBtn.setText("Update Todo")
            todoTitleEdt.setText(todoTitle)
            todoDescriptionEdt.setText(todoDes)
        }else{
            addupdateBtn.setText("Add Todo")
        }

        addupdateBtn.setOnClickListener{
            val todoTitle = todoTitleEdt.text.toString()
            val todoDescription = todoDescriptionEdt.text.toString()

            if (todoType.equals("Edit")){
                if (todoTitle.isNotEmpty() && todoDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM yyyy - HH:mm")
                    val currentDate:String =sdf.format(Date())
                    val updateTodo = Todo(todoTitle, todoDescription, currentDate)
                    updateTodo.id= todoID
                    viewModel.updateTodo(updateTodo)

                    Toast.makeText(this, "Todo updated!", Toast.LENGTH_LONG).show()
                }
            }else{
                if (todoTitle.isNotEmpty() && todoDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    viewModel.addTodo(Todo(todoTitle, todoDescription, currentDate))
                    Toast.makeText(this, "Todo added!", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }
}