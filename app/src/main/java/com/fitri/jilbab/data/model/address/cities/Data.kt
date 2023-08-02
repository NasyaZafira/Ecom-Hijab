package com.fitri.jilbab.data.model.address.cities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val city_id: Int,
    val name: String
): Parcelable