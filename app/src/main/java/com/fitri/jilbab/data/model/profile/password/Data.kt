package com.fitri.jilbab.data.model.profile.password

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val address: String,
    val created_at: String,
    val email: String,
    val email_verified_at: String,
    val id_user: Int,
    val name: String,
    val role: String,
    val updated_at: String
): Parcelable