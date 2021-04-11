package com.example.userslist.tsk

data class TaskElement  (
    var id:Int,
    var userID: Int,
    var title: String,
    var completed: Boolean
)