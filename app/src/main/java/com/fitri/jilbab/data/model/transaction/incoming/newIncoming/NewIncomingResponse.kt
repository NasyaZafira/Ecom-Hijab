package com.fitri.jilbab.data.model.transaction.incoming.newIncoming

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewIncomingResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
): Parcelable