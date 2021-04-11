package com.example.userslist.pst

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userslist.Comment_list
import com.example.userslist.R
import com.example.userslist.TaskList
import com.example.userslist.tsk.TaskAdapter


class PostAdapter (

    private val posts: MutableList<PostElement>
) : RecyclerView.Adapter<PostAdapter.postViewHolder>() {
    class postViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tytul = itemView.findViewById<TextView>(R.id.tytulPosta)
        val body = itemView.findViewById<TextView>(R.id.body)
        val context= itemView.getContext()

        fun bind(curPost: PostElement){
            tytul.text = curPost.title
            body.text=curPost.body
        }

    }


    fun addPost(post: PostElement) {
        posts.add(post)

        notifyItemInserted(posts.size - 1)
    }



    override fun onBindViewHolder(holder: postViewHolder, position: Int) {
        val curPost = posts[position]
        holder.bind(curPost)

        holder.tytul.setOnClickListener{
            holder.tytul.setBackgroundColor(Color.rgb(0, 151, 255))
            val intent= Intent(holder.context, Comment_list::class.java)
            intent.putExtra("pozycja",position)
            holder.context.startActivity(intent)


            Thread{
                run{
                    Thread.sleep(500)
                    holder.tytul.setBackgroundColor(Color.rgb(58, 31, 124))
                }
            }.start()


        }



    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): postViewHolder {
        return postViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_post, parent, false)
        )
    }


}