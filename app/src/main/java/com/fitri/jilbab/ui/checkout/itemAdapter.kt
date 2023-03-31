package com.fitri.jilbab.ui.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.user.checkout.Order
import com.fitri.jilbab.databinding.ItemOrderBinding

class itemAdapter(
    var item: MutableList<Order>
) : RecyclerView.Adapter<itemAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            thisItem: List<Order>
        ) {
            val isOrdered = thisItem[adapterPosition]

            if (isOrdered.product.mainpicture.is_main == 1) {
                Glide.with(binding.ivPoster.context)
                    .load("https://ecom-mobile.spdev.my.id/img/products/" + isOrdered.product.mainpicture.picture)
                    .error(R.drawable.white_image)
                    .into(binding.ivPoster)
            }
            binding.tvTitle.text = isOrdered.product.product_name
            binding.tvPrice.formatPrice(isOrdered.product.price)
            binding.textView14.text = isOrdered.qty.toString() + " Item"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item)
    }

    override fun getItemCount() = item.size

}