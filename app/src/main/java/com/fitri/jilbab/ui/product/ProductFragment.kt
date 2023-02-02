package com.fitri.jilbab.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.FragmentProductBinding
import com.fitri.jilbab.databinding.FragmentProfileBinding

class ProductFragment : Fragment(R.layout.fragment_product){
    private var binding : FragmentProductBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)

    }
}