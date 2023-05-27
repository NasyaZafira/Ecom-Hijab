package com.fitri.jilbab.data.model.transaction.sent.newSent

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize

data class NewSentResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
) : Parcelable
