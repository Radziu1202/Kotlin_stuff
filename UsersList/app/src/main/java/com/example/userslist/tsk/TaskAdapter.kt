package com.example.userslist.tsk

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userslist.R
import com.example.userslist.TaskList


class TaskAdapter (

        private val tasks: MutableList<TaskElement>
    ) : RecyclerView.Adapter<TaskAdapter.taskViewHolder>() {
        class taskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val title = itemView.findViewById<TextView>(R.id.tytulPosta)
            val completed = itemView.findViewById<TextView>(R.id.body)


            fun bind(curTask: TaskElement){
                title.text = curTask.title
                if(curTask.completed){
                    completed.text="done"
                }
                else{
                    completed.text="todo"
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskViewHolder {
            return taskViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.activity_task, parent, false)
            )
        }

        fun addTask(task: TaskElement) {
            tasks.add(task)
          //  Log.d("title",task.title)
            notifyItemInserted(tasks.size - 1)
        }



        override fun onBindViewHolder(holder: taskViewHolder, position: Int) {
            val curTask = tasks[position]
            holder.bind(curTask)

        }

        override fun getItemCount(): Int {
            return tasks.size
        }
    }