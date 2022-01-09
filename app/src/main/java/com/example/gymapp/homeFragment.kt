package com.example.gymapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class homeFragment : Fragment(),TodoClickInterface, TodoClickDeleteInterface,
    TodoClickDoneInterface {
    lateinit var viewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lateinit var todosRV: RecyclerView
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        todosRV = view.findViewById(R.id.idRVTodos)
        todosRV.layoutManager = LinearLayoutManager(context)

        val todoRVAdapter = TodoRVAdapter(requireContext(), this, this, this)
        todosRV.adapter = todoRVAdapter
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(
            requireActivity().application)).get(TodoViewModel::class.java)
        viewModel.allTodos.observe(requireActivity(), Observer { list ->
            list?.let {
                todoRVAdapter.updateList(it)
            }
        })

        return view
    }

    override fun onTodoClick(todo: Todo) {
        val intent = Intent(context, AddEditTodoActivity::class.java)
        intent.putExtra("todoType", "Edit")
        intent.putExtra("todoTitle", todo.todoTitle)
        intent.putExtra("todoDescription", todo.todoDescription)
        intent.putExtra("noteID", todo.id)

        startActivity(intent)

    }

    override fun onDeleteIconClick(todo: Todo) {
        viewModel.deleteTodo(todo)
        Toast.makeText(requireContext(), "${todo.todoTitle} Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onTodoDoneClick(todo: Todo) {
        val updateTodo = Todo(todo.todoTitle, todo.todoDescription, todo.timestamp)

        updateTodo.id = todo.id
        updateTodo.todoDone = !todo.todoDone
        viewModel.updateTodo(updateTodo)

    }
}