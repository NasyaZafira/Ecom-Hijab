package com.fitri.jilbab.ui.product

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.FragmentProductBinding
import com.fitri.jilbab.databinding.FragmentProfileBinding

class ProductFragment : Fragment(R.layout.fragment_product){
    private var binding : FragmentProductBinding? = null
    private var r_param : String = "pasmina"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)

        r_launch_dd()
        r_launch(r_param)

    }

    private fun r_launch_dd() {
        //lacp{
        //oberverDd(
    }

    private fun r_observerDd(){
        //resulur
        //list api

//        val category = it.mutablelist
//        val arrayAdapterGender = ArrayAdapter(this, R.layout.signup_menu, category)
//        val autoCompleteGender = binding.autoCompleteTxtGender
//        autoCompleteGender.setAdapter(arrayAdapterGender)

        //dropdown onclick{
        //isi
        //r_param = isi
        //r_launch(r_param)
        //}

    }


    private fun r_launch(r_category : String) {
        //lifecyle.{
        //vm.getlist(r_category)

    }

    private fun r_observer(){
        //vm.success....{}
        //adapter.it.mutable
    }

}