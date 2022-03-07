package com.rudra.flashgoalsadmin.data.dao

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.rudra.flashgoalsadmin.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class PostDao {

    val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("posts")

     fun addPost(category: String,title: String,description: String,imageUrl: String){
        GlobalScope.launch(Dispatchers.IO) {
            val currentTime = System.currentTimeMillis()
            val post = Post(category,title,description,currentTime,imageUrl)
            postCollection.document().set(post)
        }
    }


    fun updatePost(postId: String){

    }

    fun getPostById(postId: String): Task<DocumentSnapshot> {
            return postCollection.document(postId).get()
    }

    fun sendData(postId: String){
        GlobalScope.launch {
            val post = getPostById(postId).await().toObject(Post::class.java)!!
        }
    }

}