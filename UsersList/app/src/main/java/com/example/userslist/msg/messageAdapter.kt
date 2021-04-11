package com.example.userslist.msg

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userslist.*
import com.example.userslist.tsk.TaskAdapter

class messageAdapter(


    private val messages: MutableList<Message>

) : RecyclerView.Adapter<messageAdapter.messageViewHolder>() {
    class messageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imie_nazwisko = itemView.findViewById<TextView>(R.id.fname)
        val email = itemView.findViewById<TextView>(R.id.email)
        val tasks = itemView.findViewById<Button>(R.id.tasknumber)

        val tasks_button=itemView.findViewById<Button>(R.id.tasknumber)
        val context= itemView.getContext()

        fun bind(curMessage: Message){
            imie_nazwisko.text = curMessage.imie_nazwisko
            email.text = curMessage.email
            tasks.text = curMessage.liczba_zadan + " " + curMessage.liczba_postow

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        return messageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_user, parent, false)
        )
    }

    fun addMessage(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }




    override fun onBindViewHolder(holder: messageViewHolder, position: Int) {
        val curMessage = messages[position]
        holder.bind(curMessage)
        holder.tasks_button.setOnClickListener {
            val intent=Intent(holder.context,TaskList::class.java)
            intent.putExtra("pozycja",position)
            holder.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return messages.size
    }
}