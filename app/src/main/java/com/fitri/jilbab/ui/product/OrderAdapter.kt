package com.fitri.jilbab.ui.product

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fitri.jilbab.ui.product.cancle.CancleFragment
import com.fitri.jilbab.ui.product.complete.CompleteFragment
import com.fitri.jilbab.ui.product.incoming.IncomingFragment
import com.fitri.jilbab.ui.product.packed.PackedFragment
import com.fitri.jilbab.ui.product.sent.SentFragment
import com.fitri.jilbab.ui.product.unpaid.UnpaidFragment

class OrderAdapter(fragment: OrderFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UnpaidFragment()
            1 -> IncomingFragment()
            2 -> PackedFragment()
            3 -> SentFragment()
            4 -> CompleteFragment()
            else -> CancleFragment()
        }
    }
}