package com.fitri.jilbab.data.model.transaction.complete.newComplete
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val address: String,
    val created_at: String,
    val detail: Detail,
    val email: String,
    val email_verified_at: String,
    val id_user: Int,
    val name: String,
    val role: String,
    val updated_at: String
): Parcelable