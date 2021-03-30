package com.example.gralosujca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brgame.message.message
import com.brgame.message.messageAdapter
import com.example.gralosujca.DB.DBHelper
import com.example.gralosujca.ui.login.LoginActivity


class ListActivity : AppCompatActivity() {
    companion object{
        private lateinit var messageAdapter: messageAdapter
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list)

        val button_resume=findViewById<Button>(R.id.resume)
        button_resume.setEnabled(false)
        if(MainActivity.loggedin){
            button_resume.setEnabled(true)
        }

        messageAdapter = messageAdapter(mutableListOf())


        val messageList = findViewById<RecyclerView>(R.id.lista)
        messageList.adapter = messageAdapter
        messageList.layoutManager = LinearLayoutManager(this)


        for(a in LoginActivity.db.allMessage.sortedByDescending { it.score }.take(10) ) {
            messageAdapter.addMessage(a)
        }


        button_resume.setOnClickListener(){
            Thread(){
                run{
                }
                runOnUiThread(){
                    val  intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                }
            }.start()

            Thread(){
                run{
                    Thread.sleep(1000)
                }
                runOnUiThread(){

                }
            }


        }


    }


    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
        LoginActivity.db.allMessage

    }
}