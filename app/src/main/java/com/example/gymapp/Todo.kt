package com.example.gymapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todosTable")
class Todo (@ColumnInfo(name="title")val todoTitle:String,
            @ColumnInfo(name="description")val todoDescription: String,
            @ColumnInfo(name="timestamp")val timestamp: String,
            @ColumnInfo(name="isDone") var todoDone: Boolean = false,
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}