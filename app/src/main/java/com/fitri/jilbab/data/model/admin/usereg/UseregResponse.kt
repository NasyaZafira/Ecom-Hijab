package com.fitri.jilbab.data.model.admin.usereg

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UseregResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
): Parcelable