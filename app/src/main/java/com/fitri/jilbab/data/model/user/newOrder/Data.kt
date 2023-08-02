package com.fitri.jilbab.data.model.user.newOrder

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val products: List<Product>,
    val snaptoken: String,
    val user: User
): Parcelable