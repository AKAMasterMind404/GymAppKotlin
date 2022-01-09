package com.example.gymapp

import androidx.lifecycle.LiveData

class TodoRepository(private val todoDao: TodoDao){

    val alltodos: LiveData<List<Todo>> = todoDao.getAllTodos()

    fun insert(todo: Todo){
        todoDao.insert(todo)
    }

    fun delete(todo: Todo){
        todoDao.delete(todo)
    }

    fun update(todo: Todo){
        todoDao.update(todo)
    }

    fun doneTodo(todo: Todo){
        todoDao.doneTodo(todo)
    }
}