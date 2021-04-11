package com.example.userslist.msg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import com.example.userslist.MainActivity
import com.example.userslist.R
import com.example.userslist.TaskList
import com.example.userslist.tsk.TaskAdapter
import java.security.AccessController.getContext

class User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

    }
}