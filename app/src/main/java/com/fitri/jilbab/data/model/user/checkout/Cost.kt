package com.fitri.jilbab.data.model.user.checkout

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cost(
    val cost: List<CostX>,
    val description: String,
    val service: String
): Parcelable