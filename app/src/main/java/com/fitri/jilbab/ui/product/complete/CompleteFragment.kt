package com.fitri.jilbab.ui.product.complete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.FragmentCompleteBinding
import com.fitri.jilbab.databinding.FragmentTransactionBinding

class CompleteFragment : Fragment() {
    private var _binding: FragmentCompleteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }
}