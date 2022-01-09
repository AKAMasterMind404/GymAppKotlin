package com.example.gymapp

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TodoDao {

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(todo: Todo)

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Update
    fun doneTodo(todo: Todo)

    @Query("SELECT * FROM todosTable ORDER BY timestamp ASC")
    fun getAllTodos(): LiveData<List<Todo>>


}