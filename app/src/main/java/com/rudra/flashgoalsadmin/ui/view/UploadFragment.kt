package com.rudra.flashgoalsadmin.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.rudra.flashgoalsadmin.R
import com.rudra.flashgoalsadmin.data.dao.PostDao
import com.rudra.flashgoalsadmin.databinding.FragmentUploadBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class UploadFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var postDao: PostDao

    //    binding
    private var _binding: FragmentUploadBinding? =null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onResume() {
        super.onResume()
        val items = resources.getStringArray(R.array.categories)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item,items)
        binding.autoCompleteTextView.setAdapter(adapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)

        binding.btnPostData.setOnClickListener{
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            val category = binding.autoCompleteTextView.text.toString()
            val imageUrl = binding.etImageUrl.text.toString()

            if (title.isEmpty() || description.isEmpty() || category.isEmpty() || imageUrl.isEmpty()){
                Toast.makeText(context,title+description+category+imageUrl,Toast.LENGTH_SHORT).show()
            }else{
                postDao = PostDao()
                postDao.addPost(category, title ,description, imageUrl )
                binding.autoCompleteTextView.text?.clear()
                binding.etTitle.text?.clear()
                binding.etDescription.text?.clear()
                binding.etImageUrl.text?.clear()
            }
        }

        binding.btnPreview.setOnClickListener{
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()
            val category = binding.autoCompleteTextView.text.toString()
            val imageUrl = binding.etImageUrl.text.toString()
            if(title.isNotEmpty() || description.isNotEmpty() || category.isNotEmpty() || imageUrl.isNotEmpty() ){
                activity?.let{
                    val intent = Intent (it, PreviewActivity::class.java)
                    intent.putExtra("key_title",title)
                    intent.putExtra("key_desc",description)
                    intent.putExtra("key_category",category)
                    intent.putExtra("key_image",imageUrl)
                    it.startActivity(intent)
                }
            }
            else{
                Toast.makeText(context,"Empty Fields!!",Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UploadFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}