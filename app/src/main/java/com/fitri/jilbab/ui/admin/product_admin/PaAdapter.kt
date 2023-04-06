package com.fitri.jilbab.ui.admin.product_admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.admin.product.listNew.Data
import com.fitri.jilbab.databinding.ItemProductadmBinding

class PaAdapter(
    var prAdmin: MutableList<Data>,
    private val onDetail: (Data, Int) -> Unit
): RecyclerView.Adapter<PaAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemProductadmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            proAdmin : List<Data>,
            onDetail: (Data, Int) -> Unit
        ) {
            val isProduct = proAdmin[adapterPosition]

            if (isProduct.is_main == 1 ) {
            Glide.with(binding.imgProduct.context)
                .load("https://ecom-mobile.spdev.my.id/img/products/" + isProduct.picture )
                .error(R.drawable.white_image)
                .into(binding.imgProduct)
            }
            binding.isName.text = isProduct.product_name
            binding.isPrice.formatPrice(isProduct.price.toString())
            binding.isStock.text = "Sisa stok : " + isProduct.stock
            binding.btnEdit.setOnClickListener {
                onDetail(isProduct, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemProductadmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(prAdmin, onDetail)
    }
    override fun getItemCount() = prAdmin.size
}