package com.shop.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shop.presentation.ui.GuitarsFragment
import com.shop.presentation.ui.AccessoriesFragment

class VpAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0) {
            return GuitarsFragment()
        }
        return AccessoriesFragment()
    }
}