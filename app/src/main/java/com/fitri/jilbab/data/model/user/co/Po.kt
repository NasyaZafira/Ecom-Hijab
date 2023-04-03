package com.fitri.jilbab.data.model.user.co

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Po(
    val code: String,
    val costs: List<Cost>,
    val name: String
): Parcelable