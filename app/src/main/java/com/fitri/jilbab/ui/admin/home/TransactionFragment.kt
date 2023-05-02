package com.fitri.jilbab.ui.admin.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fitri.jilbab.databinding.FragmentHomeAdminBinding
import com.fitri.jilbab.databinding.FragmentTransactionBinding
import com.fitri.jilbab.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class TransactionFragment : Fragment() {
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTransactionBinding.bind(view)

        binding.layoutBaru.setOnClickListener {
            val i = Intent(requireContext(), LoginActivity::class.java)
            startActivity(i)
        }
    }