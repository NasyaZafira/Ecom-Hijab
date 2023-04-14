package com.fitri.jilbab.data.model.user.review.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail(
    val address: String,
    val created_at: String,
    val date_of_birth: String,
    val gender: String,
    val id_detail: Int,
    val id_user: String,
    val phone: String,
    val profile_picture: String,
    val updated_at: String
): Parcelable