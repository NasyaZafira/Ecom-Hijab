package com.fitri.jilbab.ui.address

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.data.model.address.Data
import com.fitri.jilbab.databinding.ItemAddressBinding

class AddressAdapter(
    var address: MutableList<Data>,
    private val onDetail: (Data, Int) -> Unit
) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            userAddress: List<Data>,
            onDetail: (Data, Int) -> Unit

        ) {
            val isAddress = userAddress[adapterPosition]

            binding.isName.text = isAddress.name
            binding.isPhone.text = isAddress.phone
            binding.isAddress.text = isAddress.address
            binding.isDetailAddress.text = isAddress.detail_address
            if (isAddress.is_main_address == 1) {
                binding.icDefault.visibility = View.VISIBLE
            } else {
                binding.icDefault.visibility = View.GONE
            }
            binding.itemAddressUser.setOnClickListener {
                onDetail(isAddress, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(address, onDetail)
    }
    override fun getItemCount() = address.size
}