package com.fitri.jilbab.ui.search.categoryUsr

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.admin.category.Data
import com.fitri.jilbab.databinding.ItemListCategoryBinding

class CatAdapter(
    var cgUser: MutableList<Data>,
    private val onDetailCLick: (Data) -> Unit
): RecyclerView.Adapter<CatAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemListCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            category: List<Data>,
            onDetailCLick: (Data) -> Unit
        ) {
            val isCategory = category[position]

            Glide.with(binding.imgProduct.context)
                .load("https://ecom-mobile.spdev.my.id/img/category/" + isCategory.category_image)
                .error(R.drawable.white_image)
                .into(binding.imgProduct)
            binding.isName.text = isCategory.category_name
            binding.itemListCat.setOnClickListener {
                onDetailCLick(isCategory)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatAdapter.ViewHolder {
        val view = ItemListCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: CatAdapter.ViewHolder, position: Int) {
        holder.bind(cgUser, onDetailCLick)

    }
    override fun getItemCount() = cgUser.size

}