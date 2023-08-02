package com.fitri.jilbab.data.model.user.review.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val created_at: String,
    val id_product: String,
    val id_review: Int,
    val id_user: String,
    val product: Product,
    val rating: String,
    val review: String,
    val review_date: String,
    val updated_at: String,
    val user: User
): Parcelable