package com.fitri.jilbab.data.model.user.newCo
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Po(
    val code: String,
    val costs: List<Cost>,
    val name: String
): Parcelable