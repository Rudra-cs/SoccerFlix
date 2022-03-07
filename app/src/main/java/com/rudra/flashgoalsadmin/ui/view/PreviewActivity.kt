package com.rudra.flashgoalsadmin.ui.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.rudra.flashgoalsadmin.R
import com.rudra.flashgoalsadmin.databinding.ActivityPreviewBinding
import com.rudra.flashgoalsadmin.utils.Utils

class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val title = intent.getStringExtra("key_title")
        val desc = intent.getStringExtra("key_desc")
        val image = intent.getStringExtra("key_image")
        val category = intent.getStringExtra("key_category")
        val currentTime = System.currentTimeMillis()


        binding.previewTitle.text = title
        binding.PreviewDesc.text = desc
        binding.previewCategory.text = category
        binding.previewTime.text = Utils.getTimeAgo(currentTime)

        //      Load Image with Glide Caching
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(applicationContext).load(image)
            .thumbnail(Glide.with(applicationContext).load(R.drawable.nopic))
            .apply(requestOptions).into(binding.previewImage)

//        Setting up collapsable toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbarLayout
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.news)
        Palette.from(bitmap).generate{
            if(it!=null){
                binding.collapsingToolbar.setContentScrimColor(it.getMutedColor(R.attr.colorPrimary))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
    }

}