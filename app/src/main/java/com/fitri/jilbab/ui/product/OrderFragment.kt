package com.fitri.jilbab.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.databinding.FragmentOrderBinding
import com.fitri.jilbab.ui.admin.home.HomeAdapter
import com.fitri.jilbab.ui.home.HomeFragment
import com.fitri.jilbab.ui.product.cancle.CancleFragment
import com.fitri.jilbab.ui.product.complete.CompleteFragment
import com.fitri.jilbab.ui.product.incoming.IncomingFragment
import com.fitri.jilbab.ui.product.packed.PackedFragment
import com.fitri.jilbab.ui.product.sent.SentFragment
import com.fitri.jilbab.ui.product.unpaid.UnpaidFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOrderBinding.bind(view)

        SharedPref.idNav = 2
        binding.viewPager.apply {
            this.adapter = OrderAdapter(this@OrderFragment)
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Belum Bayar"
                1 -> "Menunggu"
                2 -> "Dikemas"
                3 -> "Dikirim"
                4 -> "Selesai"
                else -> "DIbatalkan"
            }
        }.attach()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}