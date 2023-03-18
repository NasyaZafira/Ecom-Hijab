package com.fitri.jilbab.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.admin.product.list.Data
import com.fitri.jilbab.databinding.ItemProductadmBinding
import com.fitri.jilbab.databinding.ItemProductusrBinding

class PuAdapter (
    var prUser: MutableList<Data>
): RecyclerView.Adapter<PuAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemProductusrBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            proUser : List<Data>,
        ) {
            val isProduct = proUser[adapterPosition]

            if (isProduct.is_main == 1 ) {
                Glide.with(binding.imgProduct.context)
                    .load("https://ecom-mobile.spdev.my.id/img/products/" + isProduct.picture )
                    .error(R.drawable.white_image)
                    .into(binding.imgProduct)
            }
            binding.isName.text = isProduct.product_name
            binding.isPrice.text = isProduct.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemProductusrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(prUser)
    }
    override fun getItemCount() = prUser.size
}