package com.example.gymapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymapp.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity(), TodoClickInterface, TodoClickDeleteInterface,
    TodoClickDoneInterface {
    lateinit var todosRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var viewModel: TodoViewModel
    private lateinit var auth: FirebaseAuth

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        todosRV = findViewById(R.id.idRVTodos)
        addFAB = findViewById(R.id.idFABAddTodo)
        todosRV.layoutManager = LinearLayoutManager(this)

        auth = FirebaseAuth.getInstance()

        val logoutButton: Button = findViewById(R.id.btLogout)
        logoutButton.setOnClickListener(){
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

        val todoRVAdapter = TodoRVAdapter(this, this, this, this)
        todosRV.adapter = todoRVAdapter
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TodoViewModel::class.java)
        viewModel.allTodos.observe(this, Observer { list ->
            list?.let {
                todoRVAdapter.updateList(it)
            }
        })

        addFAB.setOnClickListener{
            val intent = Intent(this@HomeActivity, AddEditTodoActivity::class.java)
            startActivity(intent)
        }


    }


    override fun onTodoClick(todo: Todo) {
        val intent = Intent(this@HomeActivity, AddEditTodoActivity::class.java)
        intent.putExtra("todoType", "Edit")
        intent.putExtra("todoTitle", todo.todoTitle)
        intent.putExtra("todoDescription", todo.todoDescription)
        intent.putExtra("noteID", todo.id)

        startActivity(intent)

    }

    override fun onDeleteIconClick(todo: Todo) {
        viewModel.deleteTodo(todo)
        Toast.makeText(this, "${todo.todoTitle} Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onTodoDoneClick(todo: Todo) {
        val updateTodo = Todo(todo.todoTitle, todo.todoDescription, todo.timestamp)

        updateTodo.id = todo.id
        updateTodo.todoDone = !todo.todoDone
        viewModel.updateTodo(updateTodo)

    }


}
//{
//
//    private lateinit var auth: FirebaseAuth
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//
//        auth = FirebaseAuth.getInstance()
//
//        val logoutButton: Button = findViewById(R.id.btLogout)
//        logoutButton.setOnClickListener(){
//            auth.signOut()
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//    }
//}