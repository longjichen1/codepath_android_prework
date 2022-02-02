package com.example.todoapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskCardAdapter(val taskCards: List<TaskCard>,
                      val longClickListener: TaskCardAdapter.OnLongClickListener,
                      val deleteClickListener: TaskCardAdapter.OnDeleteListener) :
    RecyclerView.Adapter<TaskCardAdapter.TaskCardViewHolder>() {

    interface OnDeleteListener{
        fun onCardLongClicked(position:Int)
    }
    interface OnLongClickListener{
        fun onCardLongClicked(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskCardViewHolder {

        val contactView =  LayoutInflater.from(parent.context).inflate(R.layout.layout_taskcard, parent, false)
        return TaskCardViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: TaskCardViewHolder, position: Int) {
        val months = mapOf(-1 to "Invalid Date", 1 to "January", 2 to "February", 3 to "March",
                            4 to "April", 5 to "May", 6 to "June", 7 to "July", 8 to "August",
                            9 to "September", 10 to "October", 11 to "November", 12 to "December")

        val taskcard = taskCards[position]
        holder.taskView.text = taskcard.task
        var date: String = months[taskcard.month] + ' '
        Log.println(Log.ERROR, "Day", taskcard.day.toString())
        if (taskcard.day.toString() != "-1" && taskcard.day > 0 && taskcard.day < 31) date+= taskcard.day.toString() + ' '
        if (taskcard.year.toString() != "-1" && taskcard.year>2000 && taskcard.year<3000) date+= (taskcard.year.toString() + ' ')
        if (taskcard.month <= 0 || taskcard.month > 12) date = ""
        holder.dateView.text = date
    }

    override fun getItemCount(): Int {
        return taskCards.size
    }

    inner class TaskCardViewHolder (val view: View) : RecyclerView.ViewHolder(view){
        val taskView: TextView
        val dateView: TextView
        val cardView: androidx.cardview.widget.CardView
        val xView: Button
        init{
            taskView = view.findViewById(R.id.Task)
            dateView = view.findViewById(R.id.TaskDate)
            cardView = view.findViewById(R.id.card)
            xView = view.findViewById<Button>(R.id.deleteTask)
            xView.setOnClickListener{
                deleteClickListener.onCardLongClicked(adapterPosition)
                true
            }
            cardView.setOnLongClickListener {
                longClickListener.onCardLongClicked(adapterPosition)
                true
            }
        }
    }
}