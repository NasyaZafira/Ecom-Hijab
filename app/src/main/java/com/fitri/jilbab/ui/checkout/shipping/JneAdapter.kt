package com.fitri.jilbab.ui.checkout.shipping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.user.newCo.Jne
import com.fitri.jilbab.databinding.ItemKurirBinding

class JneAdapter(
    var jne: MutableList<Jne>,
    private val onCLick: (Jne) -> Unit
) : RecyclerView.Adapter<JneAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemKurirBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            courier: List<Jne>,
            onCLick: (Jne) -> Unit
        ) {
            val isJne = courier[position]
            for (i: Int in 0 until isJne.costs.size) {
                binding.isName.text = isJne.costs[i].service
                binding.isEtd.text = "Estimasi kedatangan " + isJne.costs[i].cost[i].etd + " hari"
                binding.isOngkir.formatPrice(isJne.costs[i].cost[i].value.toString())
            }
            binding.itemKurir.setOnClickListener {
                onCLick(isJne)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemKurirBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(jne, onCLick)
    }

    override fun getItemCount() = jne.size
}