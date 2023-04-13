package com.fitri.jilbab.ui.admin.home.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.admin.category.Data
import com.fitri.jilbab.databinding.ItemCategoryBinding


class CategoryAdapter(
    var cgAdmin: MutableList<Data>,
    //private val onDetailClick: (Data) -> Unit
): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            category: List<Data>,
            //onDetailClick: (Data) -> Unit
        ) {
            val isCategory = category[position]

            Glide.with(binding.imgProduct.context)
                .load("https://ecom-mobile.spdev.my.id/img/category/" + isCategory.category_image)
                .error(R.drawable.white_image)
                .into(binding.imgProduct)
            binding.isName.text = isCategory.category_name
//            binding.isStock.text = "Total Produk : " + isCategory.

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cgAdmin) //onDetailClick)
    }
    override fun getItemCount() = cgAdmin.size
}