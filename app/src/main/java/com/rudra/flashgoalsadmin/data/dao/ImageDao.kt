package com.rudra.flashgoalsadmin.data.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.rudra.flashgoalsadmin.data.model.ImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageDao {

    private val db = FirebaseFirestore.getInstance()
    private val imageCollection = db.collection("images")

    fun addImage(image: ImageModel){
        image.let {
            GlobalScope.launch(Dispatchers.IO) {
                imageCollection.document().set(it)
            }
        }
    }
}