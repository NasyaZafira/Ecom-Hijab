package com.fitri.jilbab.data.model.user.newCo
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CostX(
    val etd: String,
    val note: String,
    val value: Int
): Parcelable