package com.fitri.jilbab.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.user.cart.Data
import com.fitri.jilbab.databinding.ItemCartBinding

class CartAdapter(
    var cart                : MutableList<Data>,
    private val onRemove    : (Data, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            userCart : List<Data>,
            onRemove    : (Data, Int) -> Unit
        ){
            val isCart = userCart[absoluteAdapterPosition]

            binding.tvTitle.text = isCart.product.product_name
            binding.tvPrice.formatPrice(isCart.product.price)
            binding.tvCount.text = "X" + isCart.qty.toString()
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