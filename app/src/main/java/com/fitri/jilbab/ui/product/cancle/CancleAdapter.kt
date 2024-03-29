package com.fitri.jilbab.ui.product.cancle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.transaction.cancle.newCancle.Data

import com.fitri.jilbab.databinding.ItemOrderUserBinding

class CancleAdapter(
    var cancle: MutableList<Data>
) : RecyclerView.Adapter<CancleAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemOrderUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            ispacked: List<Data>
        ) {
            val a = ispacked[adapterPosition]

            binding.idOrder.text = a.id_order
            binding.dateOrder.text = a.order_date
            binding.rvOrder.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = CoAdapter(a.products.toMutableList())
            }

            var total = 0
            var tampungan = 0
            var com_1 = 0
            var com_2 = 0

            if (a.products.size > 0) {
                for (i: Int in 0 until a.products.toMutableList().size) {
                    com_1 = a.products.toMutableList()[i].product.price.toInt()
                    com_2 = a.products.toMutableList()[i].qty
                    tampungan = com_1 * com_2
                    total = total + tampungan
                }
            }

            binding.txtTotalPrice.formatPrice(total.toString())
            binding.isEkspedisi.text = a.courier
            if (!a.no_resi.isNullOrBlank()) {
                binding.isResi.text = a.no_resi
            } else {
                binding.isResi.text = "-"
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemOrderUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cancle)
    }

    override fun getItemCount() = cancle.size
}