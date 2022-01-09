package com.example.gymapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TodoRVAdapter(
    val context: Context,
    val todoClickInterface: TodoClickInterface,
    val todoClickDeleteInterface: TodoClickDeleteInterface,
    val todoClickDoneInterface: TodoClickDoneInterface
    ): RecyclerView.Adapter<TodoRVAdapter.ViewHolder>() {


    private val allTodos = ArrayList<Todo>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var todoCard = itemView.findViewById<CardView>(R.id.idTVTodoCard)
            val todoTV = itemView.findViewById<TextView>(R.id.idTVTodoTitle)
            val timeTV = itemView.findViewById<TextView>(R.id.idTVTimestamp)
            val deleteTV = itemView.findViewById<ImageView>(R.id.idTVDelete)
            val checkTV = itemView.findViewById<CheckBox>(R.id.idTVCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.todo_rv_item, parent, false)
        return ViewHolder(itemView)
    }

    private fun toggleStrikeThrough(todoCard: CardView, isChecked: Boolean){
        if (isChecked){
            todoCard.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        }else{
            todoCard.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_500))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.todoTV.text = allTodos[position].todoTitle
        holder.timeTV.text = "Last Updated: " + allTodos[position].timestamp
        holder.checkTV.isChecked = allTodos[position].todoDone

        toggleStrikeThrough(holder.todoCard, holder.checkTV.isChecked)

        holder.deleteTV.setOnClickListener{
            todoClickDeleteInterface.onDeleteIconClick(allTodos[position])
        }

        holder.checkTV.setOnClickListener{
            todoClickDoneInterface.onTodoDoneClick(allTodos[position])
        }

        holder.itemView.setOnClickListener{
            todoClickInterface.onTodoClick(allTodos[position])
        }
    }

    override fun getItemCount(): Int {
        return allTodos.size
    }

    fun updateList(newList: List<Todo>){
        allTodos.clear()
        allTodos.addAll(newList)
        notifyDataSetChanged()

    }
}


interface TodoClickDeleteInterface{
    fun onDeleteIconClick(todo: Todo)
}

interface TodoClickInterface{
    fun onTodoClick(todo: Todo)
}

interface TodoClickDoneInterface{
    fun onTodoDoneClick(todo: Todo)
}

