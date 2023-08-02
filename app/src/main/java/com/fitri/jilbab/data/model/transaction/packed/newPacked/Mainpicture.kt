package com.fitri.jilbab.data.model.transaction.packed.newPacked
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mainpicture(
    val created_at: String,
    val id_picture: Int,
    val id_product: String,
    val is_main: Int,
    val picture: String,
    val updated_at: String
): Parcelable