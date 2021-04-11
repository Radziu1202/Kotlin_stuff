package com.example.userslist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userslist.comm.CommentAdapter
import com.example.userslist.pst.PostAdapter
import com.example.userslist.tsk.TaskAdapter


private lateinit var commentAdapter: CommentAdapter
class Comment_list : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)
        val users_button=findViewById<Button>(R.id.lista_userów_button)
        val profile_button=findViewById<Button>(R.id.back2profile)
        val post_title=findViewById<TextView>(R.id.PostTitle)


        commentAdapter = CommentAdapter(mutableListOf())
        val comment_list = findViewById<RecyclerView>(R.id.comment_list)
        comment_list.adapter = commentAdapter
        comment_list.layoutManager = LinearLayoutManager(this)


        var position = intent.getIntExtra("pozycja",0) +1
        var found=false
        var iterator=0
        while ( found==false){

            if (lista_postów[iterator].id==position){
                post_title.text= lista_postów[iterator].title
                found=true
            }
            else if(iterator >10000){
                found=true
            }
            else{
                iterator += 1
            }

        }

        for (i in 0 until lista_commentów.size){
            if(lista_commentów[i].postID.equals(position)){
                commentAdapter.addComment(lista_commentów[i])
            }
        }

        profile_button.setOnClickListener {
            this.finish()
        }

        users_button.setOnClickListener {
            Thread{
                run{

                }
                runOnUiThread{
                    val intent= Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent)

                }
            }.start()



        }

    }
}