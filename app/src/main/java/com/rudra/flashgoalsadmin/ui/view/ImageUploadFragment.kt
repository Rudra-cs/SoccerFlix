package com.rudra.flashgoalsadmin.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.rudra.flashgoalsadmin.R
import com.rudra.flashgoalsadmin.data.dao.ImageDao
import com.rudra.flashgoalsadmin.data.model.ImageModel
import com.rudra.flashgoalsadmin.databinding.FragmentImageUploadBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ImageUploadFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    //    binding
    private var _binding: FragmentImageUploadBinding? = null
    private val binding get() = _binding!!

    lateinit var imageUri: Uri
    private var imageCode = 0

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
        _binding = FragmentImageUploadBinding.inflate(inflater, container, false)

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imageUri = it!!
                binding.addImage.setImageURI(it)
            }
        )

        binding.addImage.setOnClickListener {
            getImage.launch("image/*")
            imageCode = 1
        }
        binding.btnUpload.setOnClickListener{
            if (imageCode == 0){
                Toast.makeText(context,"Choose an Image",Toast.LENGTH_SHORT).show()
            }else{
                binding.llProgressBar.llPbar.visibility = View.VISIBLE
                uploadImage()
            }

        }
        binding.btnShowAllImage.setOnClickListener{
            startActivity(Intent(context,ShowImage::class.java))
        }
        return binding.root
    }

//    private fun uploadImageUrl() {
//
////        TODO("Setup the firestore for the image url this one currently does not find the object")
//        val fileName = binding.etImageDesc.text
//        val storageReferences = FirebaseStorage.getInstance().getReference("images")
////        Toast.makeText(context,fileName,Toast.LENGTH_SHORT).show()
//
//        storageReferences.child("/$fileName").downloadUrl.addOnSuccessListener {
//            val imageDao = ImageDao()
//            val imageUrl = ImageModel(it.toString())
//            imageDao.addImage(imageUrl)
//            Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
//            Log.d("pokemon",it.toString())
//        }.addOnFailureListener {
//            Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun uploadImage() {
//        val formatter = SimpleDateFormat("yyyy_MM_dd_MM_mm-ss",Locale.getDefault())
//        val now = Date()
//        val fileName = formatter.format(now)

        val fileName = binding.etImageDesc.text
        val storageReferences = FirebaseStorage.getInstance().getReference("images/$fileName")

//      Storage Reference
        storageReferences.putFile(imageUri)
            .addOnSuccessListener {
                binding.addImage.setImageResource(R.drawable.photo)
                binding.llProgressBar.llPbar.visibility = View.GONE
                Toast.makeText(context,"Successfully Uploaded",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                binding.llProgressBar.llPbar.visibility = View.GONE
                Toast.makeText(context,"Failure In Uploading",Toast.LENGTH_SHORT).show()
            }

//        GlobalScope.launch(Dispatchers.IO) {
//            delay(5000)
//            uploadImageUrl()
//        }


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageUploadFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
