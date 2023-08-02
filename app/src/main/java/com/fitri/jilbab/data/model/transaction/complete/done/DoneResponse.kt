package com.fitri.jilbab.data.model.transaction.complete.done
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoneResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
): Parcelable