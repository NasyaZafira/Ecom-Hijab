package com.fitri.jilbab.ui.product.unpaid

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.data.model.transaction.unpaid.Data
import com.fitri.jilbab.databinding.ItemOrderUserBinding
import com.fitri.jilbab.ui.search.SearchAdapter

class UnpaidAdapter(
    var unpaid: MutableList<Data>
) : RecyclerView.Adapter<UnpaidAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemOrderUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            userUnpaid: List<Data>
        ) {
            val a = userUnpaid[adapterPosition]

            binding.idOrder.text = a.id_order


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}