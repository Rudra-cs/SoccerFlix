package com.rudra.flashgoalsadmin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rudra.flashgoalsadmin.R
import com.rudra.flashgoalsadmin.data.model.Post
import com.rudra.flashgoalsadmin.utils.Utils

class PostAdapter(options: FirestoreRecyclerOptions<Post>,val listener:IPostAdapter) : FirestoreRecyclerAdapter<Post,PostAdapter.PostViewHolder>(
    options
) {

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val categoryText: TextView = itemView.findViewById(R.id.tv_category_thumbnail)
        val titleText: TextView = itemView.findViewById(R.id.tv_title_thumbnail)
        val createdAtText: TextView = itemView.findViewById(R.id.tv_time_thumbnail)
        val postImage: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        val editBtn: ImageView = itemView.findViewById(R.id.iv_edit)
        val deleteBtn: ImageView = itemView.findViewById(R.id.iv_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder= PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cell,parent,false))
        viewHolder.editBtn.setOnClickListener{
            listener.onEditClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        viewHolder.deleteBtn.setOnClickListener{
            listener.onDeleteClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.categoryText.text = model.category
        holder.titleText.text = model.title
        holder.createdAtText.text = Utils.getTimeAgo(model.createdAt)
        Glide.with(holder.postImage.context).load(model.imageUrl).into(holder.postImage)

    }
}

interface IPostAdapter{
    fun onEditClicked(postId:String)
    fun onDeleteClicked(postId:String)
}