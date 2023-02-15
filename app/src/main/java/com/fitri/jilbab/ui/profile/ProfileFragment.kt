package com.fitri.jilbab.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.databinding.FragmentProfileBinding
import com.fitri.jilbab.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()
    private val _binding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        lifecycleScope.launch {
            viewModel.detailProfile()
        }
        setupObserver()
        button()

    }

    private fun setupObserver() {
        val loading = CustomLoadingDialog(requireContext())
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
        viewModel.userDetail.observe(viewLifecycleOwner) {
            _binding.isUname.text = it.data.name
            _binding.isEmail.text = it.data.email
            _binding.isNumb.text = it.data.detail.phone.toString()
            _binding.isGender.text = it.data.detail.gender.toString()
            _binding.isBirth.text = it.data.detail.date_of_birth.toString()
            _binding.isAdress.text = it.data.address.toString()
        }
    }

    private fun button() {
        _binding.btnLogout.setOnClickListener {
            SharedPref.clear()
            val i = Intent(requireContext(), LoginActivity::class.java)
            startActivity(i)
            finishAffinity(requireActivity())

        }
    }
}