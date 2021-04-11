package com.example.userslist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userslist.pst.PostAdapter
import com.example.userslist.tsk.TaskAdapter


private lateinit var taskAdapter: TaskAdapter
private lateinit var postAdapter: PostAdapter
class TaskList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        val users_button=findViewById<Button>(R.id.users_butt)
        val switch_button=findViewById<Switch>(R.id.switchTasks)
        val switch_posts=findViewById<Switch>(R.id.switchPosts)
        val posty_text=findViewById<TextView>(R.id.posty_text)
        val zadania_text=findViewById<TextView>(R.id.zadania_text)
        val user=findViewById<TextView>(R.id.User_name)

        taskAdapter = TaskAdapter(mutableListOf())
        val taskList = findViewById<RecyclerView>(R.id.taskList)
        taskList.adapter = taskAdapter
        taskList.layoutManager = LinearLayoutManager(this)


        postAdapter = PostAdapter(mutableListOf())
        val postList=findViewById<RecyclerView>(R.id.postList)
        postList.adapter=postAdapter
        postList.layoutManager=LinearLayoutManager(this)

        switch_button.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked){
                taskList.visibility = View.GONE
                zadania_text.visibility=View.GONE
            }
            else{
                taskList.visibility = View.VISIBLE
                zadania_text.visibility=View.VISIBLE
            }
        }

        switch_posts.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked){
                postList.visibility = View.GONE
                posty_text.visibility=View.GONE
            }
            else{
                postList.visibility = View.VISIBLE
                posty_text.visibility=View.VISIBLE
            }
        }

        var position = intent.getIntExtra("pozycja",0) +1

        user.text=lista[position-1].imie_nazwisko;
        for (i in 0 until lista_tasków.size){
            if(lista_tasków[i].userID.equals(position)){
                taskAdapter.addTask(lista_tasków[i])
            }
        }

        for (i in 0 until lista_postów.size){
            if(lista_postów[i].userID.equals(position)){
                postAdapter.addPost(lista_postów[i])
            }
        }

        users_button.setOnClickListener {
            this.finish()
        }



    }



}