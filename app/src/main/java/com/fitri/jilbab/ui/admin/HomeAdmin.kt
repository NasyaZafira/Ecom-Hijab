package com.fitri.jilbab.ui.admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.databinding.FragmentHomeAdminBinding
import com.fitri.jilbab.ui.admin.home.HomeAdapter
import com.fitri.jilbab.ui.login.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeAdmin : Fragment() {

    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeAdminBinding.bind(view)

        binding.viewPager.apply {
            this.adapter = HomeAdapter(this@HomeAdmin)
        }

        binding.btnOut.setOnClickListener {
            SharedPref.clear()
            val i = Intent(requireContext(), LoginActivity::class.java)
            startActivity(i)
            ActivityCompat.finishAffinity(requireActivity())}

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Transaksi"
                else -> "Kategori"
            }
        }.attach()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}