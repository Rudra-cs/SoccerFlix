package com.rudra.flashgoalsadmin.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rudra.flashgoalsadmin.ui.view.ContentFragment
import com.rudra.flashgoalsadmin.ui.view.ImageUploadFragment
import com.rudra.flashgoalsadmin.ui.view.UploadFragment

private const val NUM_TABS = 3

public class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return UploadFragment()
            1 -> return ContentFragment()
        }
        return ImageUploadFragment()
    }
}