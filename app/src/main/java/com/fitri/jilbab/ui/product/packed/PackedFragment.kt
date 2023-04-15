package com.fitri.jilbab.ui.product.packed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.FragmentPackedBinding
import com.fitri.jilbab.databinding.FragmentTransactionBinding


class PackedFragment : Fragment() {
    private var _binding: FragmentPackedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPackedBinding.inflate(inflater, container, false)
        return binding.root
    }
}