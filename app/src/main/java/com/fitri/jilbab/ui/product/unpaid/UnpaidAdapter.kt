package com.fitri.jilbab.ui.product.unpaid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.transaction.unpaid.Data
import com.fitri.jilbab.databinding.ItemUnpaidBinding

class UnpaidAdapter(
    var unpaid: MutableList<Data>,
    private val onCancle : (Data, Int) -> Unit
) : RecyclerView.Adapter<UnpaidAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemUnpaidBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            userUnpaid: List<Data>,
            onCancle : (Data, Int) -> Unit
        ) {
            val a = userUnpaid[adapterPosition]

            binding.idOrder.text    = a.id_order
            binding.dateOrder.text  = a.order_date
            binding.rvOrder.apply {
                layoutManager       = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter             = PunpAdapter(a.products.toMutableList())
            }

            var total       = 0
            var tampungan   = 0
            var com_1       = 0
            var com_2       = 0

            if (a.products.size > 0){
                for (i : Int in 0 until a.products.toMutableList().size){
                    com_1       = a.products.toMutableList()[i].product.price.toInt()
                    com_2       = a.products.toMutableList()[i].qty
                    tampungan   = com_1 * com_2
                    total       = total + tampungan
                }
            }

            binding.txtTotalPrice.formatPrice(total.toString())
            binding.isEkspedisi.text = a.courier
            binding.btnCheckout.setOnClickListener {
                onCancle(a, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUnpaidBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(unpaid, onCancle)
    }

    override fun getItemCount() = unpaid.size
}