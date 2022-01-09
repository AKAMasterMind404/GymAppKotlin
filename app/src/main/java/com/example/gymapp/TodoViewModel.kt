package com.example.gymapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): AndroidViewModel(application) {
    val allTodos: LiveData<List<Todo>>
    val repository: TodoRepository

    init {
        val dao = TodoDatabase.getDatabase(application).getTodosDao()
        repository = TodoRepository(dao)
        allTodos = repository.alltodos
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(todo)
    }

    fun updateTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(todo)
    }

    fun addTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(todo)
    }

    fun doneTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO){
        repository.doneTodo(todo)
    }

}