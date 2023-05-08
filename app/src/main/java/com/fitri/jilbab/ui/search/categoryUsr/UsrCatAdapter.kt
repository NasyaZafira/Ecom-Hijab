package com.fitri.jilbab.ui.search.categoryUsr

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.user.cat.Data

import com.fitri.jilbab.databinding.ItemSearchBinding

class UsrCatAdapter(
    var product: MutableList<Data>,
    private val onDetailCLick: (Data) -> Unit
) : RecyclerView.Adapter<UsrCatAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            proUser: List<Data>,
            onDetailCLick: (Data) -> Unit
        ) {
            val isProduct = proUser[position]

            if (isProduct.pictures[0].is_main == 1) {
                Glide.with(binding.imgProduct.context)
                    .load("https://ecom-mobile.spdev.my.id/img/products/" + isProduct.pictures[0].picture)
                    .error(R.drawable.white_image)
                    .into(binding.imgProduct)
            }
            binding.rbFood.rating       = isProduct.rating.toFloat()
            binding.isName.text = isProduct.product_name
            binding.isPrice.formatPrice(isProduct.price)
            binding.itemSearch.setOnClickListener {
                onDetailCLick(isProduct)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(product, onDetailCLick)

    }

    override fun getItemCount() = product.size
}