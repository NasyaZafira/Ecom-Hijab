package com.fitri.jilbab.data.model.admin.usereg

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Data(
    val address: String,
    val created_at: Date,
    val detail: Detail,
    val email: String,
    val email_verified_at: String,
    val id_user: Int,
    val name: String,
    val role: String,
    val transaction_count: Int,
    val updated_at: String
): Parcelable