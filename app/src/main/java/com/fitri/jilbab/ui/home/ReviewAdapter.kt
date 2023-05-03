package com.fitri.jilbab.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.user.review.list.Data
import com.fitri.jilbab.databinding.ItemReviewBinding

class ReviewAdapter(
    var review: MutableList<Data>
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            isReview: List<Data>
        ) {
            val listReview = isReview[adapterPosition]

            try {

                if (!listReview.user.detail.profile_picture.isNullOrBlank()) {
                    Glide.with(binding.cardFoto3.context)
                        .load("https://ecom-mobile.spdev.my.id/img/profile/" + listReview.user.detail.profile_picture)
                        .error(R.drawable.white_image)
                        .into(binding.cardFoto3)
                } else {
                    Glide.with(binding.cardFoto3.context)
                        .load(R.drawable.white_image)
                        .error(R.drawable.white_image)
                        .into(binding.cardFoto3)
                }
            } catch (e: NullPointerException) {
                print("Caught the NullPointerException")
            }
            binding.txtNamaKomentator.text = listReview.user.name
            binding.ratingBar.rating = listReview.rating.toFloat()
            binding.txtIsiKomen.text = listReview.review
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(review)
    }

    override fun getItemCount() = review.size
}