package com.fitri.jilbab.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.fitri.jilbab.data.model.profile.Data
import com.fitri.jilbab.databinding.FragmentProfileBinding
import com.fitri.jilbab.ui.login.LoginActivity
import com.fitri.jilbab.ui.profile.edit.EditProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()
    private val binding get() = _binding!!
    private var dataUser: Data? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        SharedPref.idNav = 3

        lifecycleScope.launch {
            viewModel.detailProfile()
        }
        binding.btnEdit.setOnClickListener {
            val i = Intent(requireContext(), EditProfileActivity::class.java)
            i.putExtra("data", dataUser)
            startActivity(i)
            requireActivity().finish()
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
            Log.e("TAG", "setupObserver: " + it)

            dataUser = it.data

            if (it.data?.name != null) {
                binding.isUname.text = it.data.name
            } else {
                "--"
            }
            if (it.data?.email != null) {
                binding.isEmail.text = it.data.email
            } else {
                "--"
            }
            if (it.data?.detail?.phone != null) {
                binding.isNumb.text = it.data!!.detail!!.phone
            } else {
                "--"
            }
            if (it.data?.detail?.gender != null) {
                binding.isGender.text = it.data.detail.gender
            } else {
                "--"
            }
            if (it.data?.detail?.date_of_birth != null) {
                binding.isBirth.text = it.data!!.detail!!.date_of_birth
            } else {
                "--"
            }
            if (it.data?.detail?.address != null) {
                binding.isAdress.text = it.data.detail.address
            } else {
                "--"
            }
        }
    }

    private fun button() {
        binding.btnLogout.setOnClickListener {
            SharedPref.clear()
            val i = Intent(requireContext(), LoginActivity::class.java)
            startActivity(i)
            finishAffinity(requireActivity())

        }
    }
}