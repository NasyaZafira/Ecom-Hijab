package com.fitri.jilbab.ui.admin.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.admin.usereg.Data
import com.fitri.jilbab.databinding.ItemUseregBinding
import com.fitri.jilbab.ui.TimeAgo.toCustomDate

class CustomerAdapter(
    var cusAdmin: MutableList<Data>,
) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemUseregBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            customer: List<Data>,
        ) {
            val isCustomer = customer[adapterPosition]
            try {
                if (isCustomer.detail.profile_picture == null) {
                    Glide.with(binding.imgProduct.context)
                        .load(R.drawable.white_image)
                        .error(R.drawable.white_image)
                        .into(binding.imgProduct)
                } else {
                    Glide.with(binding.imgProduct.context)
                        .load("https://ecom-mobile.spdev.my.id/img/profile/" + isCustomer.detail.profile_picture)
                        .error(R.drawable.white_image)
                        .into(binding.imgProduct)
                }

                binding.isName.text = "Pelanggan : " + isCustomer.name
                binding.isEmail.text = "Email : " + isCustomer.email
                if (isCustomer.detail.phone == null) {
                    binding.isPhone.text = "Telepon : -  "
                } else {
                    binding.isPhone.text = "Telepon : " + isCustomer.detail.phone

                }
                binding.isCreated.text = "Terdaftar pada " + isCustomer.created_at.toCustomDate()
            } catch (e: NullPointerException) {
                print("Caught the NullPointerException")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUseregBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cusAdmin)
    }

    override fun getItemCount() = cusAdmin.size
}