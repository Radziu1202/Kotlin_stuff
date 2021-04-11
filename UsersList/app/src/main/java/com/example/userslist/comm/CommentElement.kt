package com.example.userslist.comm

data class CommentElement (
    var id:Int,
    var postID: Int,
    var name: String,
    var email: String,
    var body: String
)
