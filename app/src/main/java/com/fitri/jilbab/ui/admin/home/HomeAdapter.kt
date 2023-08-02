package com.fitri.jilbab.ui.admin.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fitri.jilbab.ui.admin.HomeAdmin
import com.fitri.jilbab.ui.admin.home.category.CategoryFragment

class HomeAdapter(fragment: HomeAdmin) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TransactionFragment()
            1 -> UseregFragment()
            else -> CategoryFragment()
        }
    }
}