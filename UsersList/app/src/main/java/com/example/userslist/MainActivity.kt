package com.example.userslist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userslist.comm.CommentElement
import com.example.userslist.msg.Message
import com.example.userslist.msg.messageAdapter
import com.example.userslist.pst.PostElement
import com.example.userslist.tsk.TaskAdapter
import com.example.userslist.tsk.TaskElement
import java.net.URL
import org.json.JSONArray


private lateinit var messageAdapter: messageAdapter

var lista_tasków:MutableList<TaskElement> = mutableListOf()
var lista_postów:MutableList<PostElement> = mutableListOf()
var lista_commentów:MutableList<CommentElement> = mutableListOf()
val lista: MutableList<Message> = mutableListOf()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        messageAdapter = messageAdapter(mutableListOf())

        val messageList = findViewById<RecyclerView>(R.id.messageList)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)

        Log.d("test","test1")

        Thread() {
            run {
                val url = "https://jsonplaceholder.typicode.com/users"
                val body = URL(url).readText()

                val usersJSONarray = JSONArray(body)

                val urlTask = "https://jsonplaceholder.typicode.com/todos"
                val tasks = URL(urlTask).readText()
                val tasksJSONarray= JSONArray(tasks)

                val urlPosts= "https://jsonplaceholder.typicode.com/posts"
                val posts = URL(urlPosts).readText()
                val postsJSONarray= JSONArray(posts)

                val urlComments= "https://jsonplaceholder.typicode.com/comments"
                val comments = URL(urlComments).readText()
                val commentsJSONarray= JSONArray(comments)


                val tasks_number= MutableList(usersJSONarray.length()){0}
                val posts_number=  MutableList(usersJSONarray.length()){0}


                if (tasks != "" && lista_tasków.isEmpty()) {
                    for(i in 0 until tasksJSONarray.length()){
                        val JSONobject = tasksJSONarray.getJSONObject(i)
                        var userID = JSONobject.getInt("userId")
                        var id=JSONobject.getInt("id")
                        var title=JSONobject.getString("title")
                        var completed= JSONobject.getBoolean("completed")
                        tasks_number[userID-1]=tasks_number.get(userID-1)+1
                      //  Log.d("TASK",tasks_number.toString())
                        var tsk= TaskElement(id,userID,title, completed)
                        lista_tasków.add(tsk)
                    }
                }

                if (posts != "" && lista_postów.isEmpty()) {
                    for(i in 0 until postsJSONarray.length()){
                        val JSONobject = postsJSONarray.getJSONObject(i)
                        var userID = JSONobject.getInt("userId")
                        var id=JSONobject.getInt("id")
                        var title=JSONobject.getString("title")
                        var body= JSONobject.getString("body")
                        posts_number[userID-1]=posts_number.get(userID-1)+1

                        var post= PostElement(id,userID,title, body)
                        lista_postów.add(post)
                    }
                }


                if (comments != "" && lista_commentów.isEmpty()) {
                    for(i in 0 until commentsJSONarray.length()){
                        val JSONobject = commentsJSONarray.getJSONObject(i)
                        var postID = JSONobject.getInt("postId")
                        var id=JSONobject.getInt("id")
                        var name = JSONobject.getString("name")
                        var email = JSONobject.getString("email")
                        var body = JSONobject.getString("body")
                       // tasks_number[postID-1]=tasks_number.get(postID-1)+1
                        //  Log.d("TASK",tasks_number.toString())
                        var comm=CommentElement(id,postID,name,email,body)
                        lista_commentów.add(comm)
                    }
                }


                Log.d("len",usersJSONarray.length().toString())
                if (body != "" && lista.isEmpty()) {
                    for(i in 0 until usersJSONarray.length()){
                        val JSONobject = usersJSONarray.getJSONObject(i)
                        var id = JSONobject.getInt("id")
                        var name = JSONobject.getString("name")
                        var email = JSONobject.getString("email")
                        var msg=Message(id,name,email, "Liczba zadań: " + tasks_number.get(id-1)," Liczba postów: "+ posts_number.get(id-1))
                        lista.add(i,msg)
                    }
                }





            }
            Log.d("lista", lista.size.toString())
            runOnUiThread() {
                for (i in 0 until lista.size){

                    messageAdapter.addMessage(lista[i]);
                }

            }


        }.start()



        fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.menu_main, menu)
            return true
        }

        fun onOptionsItemSelected(item: MenuItem): Boolean {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            return when (item.itemId) {
                R.id.action_settings -> true
                else -> super.onOptionsItemSelected(item)
            }
        }
    }
}