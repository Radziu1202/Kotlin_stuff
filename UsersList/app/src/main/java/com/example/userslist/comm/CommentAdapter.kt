package com.example.userslist.comm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userslist.R
import com.example.userslist.pst.PostElement

class CommentAdapter (
    private val comments: MutableList<CommentElement>
) : RecyclerView.Adapter<CommentAdapter.commentViewHolder>() {
        class commentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            var name=itemView.findViewById<TextView>(R.id.comment_name)
            var email= itemView.findViewById<TextView>(R.id.comment_mail)
            var body=itemView.findViewById<TextView>(R.id.comment_body)

            fun bind(curComm: CommentElement){
                name.text=curComm.name
                email.text=curComm.email
                body.text=curComm.body
            }

        }

    fun addComment(comment: CommentElement) {
        comments.add(comment)
        notifyItemInserted(comments.size - 1)
    }



    override fun onBindViewHolder(holder: commentViewHolder, position: Int) {
        val curComm = comments[position]
        holder.bind(curComm)

    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): commentViewHolder {
        return commentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_comment, parent, false)
        )
    }


}








