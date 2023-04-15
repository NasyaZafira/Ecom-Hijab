package com.fitri.jilbab.ui.product.unpaid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.FragmentTransactionBinding
import com.fitri.jilbab.databinding.FragmentUnpaidBinding


class UnpaidFragment : Fragment() {
    private var _binding: FragmentUnpaidBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUnpaidBinding.inflate(inflater, container, false)
        return binding.root
    }

}