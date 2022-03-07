package com.rudra.flashgoalsadmin.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.rudra.flashgoalsadmin.databinding.ActivityMainBinding
import com.rudra.flashgoalsadmin.ui.adapter.ViewPagerAdapter

val tabArray = arrayOf(
    "Upload Item",
    "All Data",
    "Upload Image")

    class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewpager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        viewpager.adapter = adapter

        TabLayoutMediator(tabLayout,viewpager){ tab,postion->

            tab.text = tabArray[postion]
        }.attach()
    }
}