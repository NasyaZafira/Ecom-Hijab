package com.fitri.jilbab.data.model.user.review

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val created_at: String,
    val id_product: String,
    val id_review: Int,
    val id_user: Int,
    val rating: String,
    val review: String,
    val review_date: String,
    val updated_at: String
): Parcelable