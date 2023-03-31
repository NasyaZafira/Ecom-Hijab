package com.fitri.jilbab.data.model.user.checkout

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CostX(
    val etd: String,
    val note: String,
    val value: Int
): Parcelable