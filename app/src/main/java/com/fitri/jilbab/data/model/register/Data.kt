package com.fitri.jilbab.data.model.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val created_at: String,
    val email: String,
    val id_user: Int,
    val name: String,
    val role: String,
    val updated_at: String
): Parcelable