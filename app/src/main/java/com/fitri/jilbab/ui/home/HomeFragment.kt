package com.fitri.jilbab.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.FragmentHomeBinding
import com.fitri.jilbab.databinding.FragmentProfileBinding

class HomeFragment : Fragment(R.layout.fragment_home){
    private var binding : FragmentHomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

    }
}