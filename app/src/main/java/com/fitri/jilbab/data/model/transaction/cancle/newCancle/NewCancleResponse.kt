package com.fitri.jilbab.data.model.transaction.cancle.newCancle

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewCancleResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
): Parcelable