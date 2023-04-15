package com.fitri.jilbab.ui.product.cancle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.FragmentCancleBinding
import com.fitri.jilbab.databinding.FragmentTransactionBinding


class CancleFragment : Fragment() {
    private var _binding: FragmentCancleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCancleBinding.inflate(inflater, container, false)
        return binding.root
    }

}