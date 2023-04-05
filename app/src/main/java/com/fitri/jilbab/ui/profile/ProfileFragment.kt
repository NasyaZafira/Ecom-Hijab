package com.fitri.jilbab.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.data.model.profile.Data
import com.fitri.jilbab.databinding.FragmentProfileBinding
import com.fitri.jilbab.ui.address.ListAddressActivity
import com.fitri.jilbab.ui.admin.home.HomeAdapter
import com.fitri.jilbab.ui.login.LoginActivity
import com.fitri.jilbab.ui.profile.edit.EditProfileActivity
import com.google.android.material.tabs.TabLayoutMediator
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
        setupObserver()

        binding.layoutChange.setOnClickListener {
            val i = Intent(requireContext(), EditProfileActivity::class.java)
            i.putExtra("data", dataUser)
            startActivity(i)
            requireActivity().finish()
        }
        binding.layoutAddress.setOnClickListener {
            val i = Intent(requireContext(), ListAddressActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }
        binding.layoutLogout.setOnClickListener {
            SharedPref.clear()
            val i = Intent(requireContext(), LoginActivity::class.java)
            startActivity(i)
            finishAffinity(requireActivity())

        }

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
        }
    }

}