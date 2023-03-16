package com.fitri.jilbab.ui.admin.product_admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.admin.product.list.Data
import com.fitri.jilbab.databinding.ItemProductadmBinding

class PaAdapter(
    var prAdmin: MutableList<Data>
): RecyclerView.Adapter<PaAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemProductadmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            proAdmin : List<Data>,
        ) {
            val isProduct = proAdmin[adapterPosition]

            if (isProduct.is_main == 1 ) {
            Glide.with(binding.imgProduct.context)
                .load("https://ecom-mobile.spdev.my.id/img/products/" + isProduct.picture )
                .error(R.drawable.white_image)
                .into(binding.imgProduct)
            }
            binding.isName.text = isProduct.product_name
            binding.isPrice.text = isProduct.price
            binding.isStock.text = "Sisa stok : " + isProduct.stock
            binding.btnEdit.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemProductadmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(prAdmin)
    }
    override fun getItemCount() = prAdmin.size
}