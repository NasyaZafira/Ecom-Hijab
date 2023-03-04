package com.fitri.jilbab.ui.admin.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fitri.jilbab.ui.admin.HomeAdmin

class HomeAdapter(fragment: HomeAdmin) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TransactionFragment()
            else -> CategoryFragment()
        }
    }
}