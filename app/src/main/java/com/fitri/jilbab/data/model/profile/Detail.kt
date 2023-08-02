package com.fitri.jilbab.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Detail(
    val address: String?,
    val created_at: String?,
    val date_of_birth: String?,
    val gender: String?,
    val id_detail: String?,
    val id_user: String?,
    val phone: String?,
    val profile_picture: String?,
    val updated_at: String?
):Parcelable