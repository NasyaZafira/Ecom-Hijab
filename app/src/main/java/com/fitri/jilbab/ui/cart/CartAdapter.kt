package com.fitri.jilbab.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.user.cart.list.Cart
import com.fitri.jilbab.databinding.ItemCartBinding

class CartAdapter(
    var cart                : MutableList<Cart>,
    private val onRemove    : (Cart, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            userCart : List<Cart>,
            onRemove    : (Cart, Int) -> Unit
        ){
            val isCart = userCart[absoluteAdapterPosition]

            if (isCart.product.mainpicture.is_main == 1 ) {
                Glide.with(binding.ivPoster.context)
                    .load("https://ecom-mobile.spdev.my.id/img/products/" + isCart.product.mainpicture.picture )
                    .error(R.drawable.white_image)
                    .into(binding.ivPoster)
            }
            binding.tvTitle.text = isCart.product.product_name
            binding.tvPrice.formatPrice(isCart.product.price)
            binding.tvCount.text = "x " + isCart.qty.toString() + "Item"
            binding.btnClose.setOnClickListener {
                onRemove(isCart, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cart, onRemove)
    }
    override fun getItemCount() = cart.size
}