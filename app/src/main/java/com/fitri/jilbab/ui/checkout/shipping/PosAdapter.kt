package com.fitri.jilbab.ui.checkout.shipping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.user.newCo.Po
import com.fitri.jilbab.databinding.ItemKurirBinding

class PosAdapter(
    var pos: MutableList<Po>,
    private val onCLick: (Po) -> Unit
) : RecyclerView.Adapter<PosAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemKurirBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            courier: List<Po>,
            onCLick: (Po) -> Unit
        ) {
            val isPos = courier[position]
            for (i: Int in 0 until isPos.costs.size) {
                binding.isName.text = isPos.costs[i].service
                binding.isEtd.text = "Estimasi kedatangan " + isPos.costs[i].cost[i].etd + " hari"
                binding.isOngkir.formatPrice(isPos.costs[i].cost[i].value.toString())
            }
            binding.itemKurir.setOnClickListener {
                onCLick(isPos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemKurirBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pos, onCLick)
    }

    override fun getItemCount() = pos.size
}