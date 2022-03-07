package com.rudra.flashgoalsadmin.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.rudra.flashgoalsadmin.data.dao.PostDao
import com.rudra.flashgoalsadmin.data.model.Post
import com.rudra.flashgoalsadmin.databinding.FragmentContentBinding
import com.rudra.flashgoalsadmin.ui.adapter.IPostAdapter
import com.rudra.flashgoalsadmin.ui.adapter.PostAdapter
import com.rudra.flashgoalsadmin.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ContentFragment : Fragment(), IPostAdapter {
    private var param1: String? = null
    private var param2: String? = null

//    binding
    private var _binding: FragmentContentBinding? =null
    private val binding get() = _binding!!

    private lateinit var adapterPost: PostAdapter
    private lateinit var postDao: PostDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContentBinding.inflate(inflater, container, false)

        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        val postCollection = postDao.postCollection
        val query = postCollection.orderBy("createdAt",Query.Direction.DESCENDING)

        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        adapterPost = PostAdapter(recyclerViewOptions,this)
        binding.rvContent.adapter = adapterPost
        binding.rvContent.layoutManager = LinearLayoutManager(context)

    }

    override fun onStart() {
        super.onStart()
        adapterPost.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterPost.stopListening()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onEditClicked(postId: String) {
        activity?.let{
            GlobalScope.launch(Dispatchers.Main){
                val postData = postDao.getPostById(postId).await().toObject(Post::class.java)!!

                val intent = Intent (it, PreviewActivity::class.java)
                intent.putExtra("key_title",postData.title)
                intent.putExtra("key_desc",postData.description)
                intent.putExtra("key_category",postData.category)
                intent.putExtra("key_image",postData.imageUrl)
                intent.putExtra("key_date",Utils.getTimeAgo(postData.createdAt))
                it.startActivity(intent)
            }
        }
    }

    override fun onDeleteClicked(postId: String) {
        TODO("Not yet implemented")
    }


}
