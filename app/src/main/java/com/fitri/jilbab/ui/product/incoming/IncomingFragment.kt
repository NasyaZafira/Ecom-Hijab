package com.fitri.jilbab.ui.product.incoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.FragmentIncomingBinding
import com.fitri.jilbab.databinding.FragmentTransactionBinding


class IncomingFragment : Fragment() {
    private var _binding: FragmentIncomingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIncomingBinding.inflate(inflater, container, false)
        return binding.root
    }
}