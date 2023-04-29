package com.fitri.jilbab.ui.product.incoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.transaction.incoming.Data
import com.fitri.jilbab.databinding.ItemOrderUserBinding
import com.fitri.jilbab.ui.product.unpaid.PunpAdapter

class Incomingadapter(
    var incom: MutableList<Data>
) : RecyclerView.Adapter<Incomingadapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemOrderUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            userUnpaid: List<Data>
        ) {
            val a = userUnpaid[adapterPosition]

            binding.idOrder.text = a.id_order
            binding.dateOrder.text = a.order_date
            binding.rvOrder.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = IncAdapter(a.products.toMutableList())
            }
            for (i: Int in 0 until userUnpaid.size) {
                val b = 0 + ((userUnpaid[i].products[i].product.price.toInt()) * (userUnpaid[i].products[i].qty))
                binding.txtTotalPrice.formatPrice(b.toString())
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemOrderUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(incom)
    }

    override fun getItemCount() = incom.size
}