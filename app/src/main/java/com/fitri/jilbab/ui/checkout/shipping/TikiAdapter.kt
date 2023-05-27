package com.fitri.jilbab.ui.checkout.shipping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.user.newCo.Tiki
import com.fitri.jilbab.databinding.ItemKurirBinding

class TikiAdapter(
    var tiki: MutableList<Tiki>,
    private val onCLick: (Tiki) -> Unit
) : RecyclerView.Adapter<TikiAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemKurirBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            courier: List<Tiki>,
            onCLick: (Tiki) -> Unit
        ) {
            val isTiki = courier[position]
            for (i: Int in 0 until isTiki.costs.size) {
                binding.isName.text = isTiki.costs[i].service
                binding.isEtd.text = "Estimasi kedatangan " + isTiki.costs[i].cost[i].etd + " hari"
                binding.isOngkir.formatPrice(isTiki.costs[i].cost[i].value.toString())
            }
            binding.itemKurir.setOnClickListener {
                onCLick(isTiki)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemKurirBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tiki, onCLick)
    }

    override fun getItemCount() = tiki.size
}