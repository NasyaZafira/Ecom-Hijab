package com.fitri.jilbab.data.model.user.latestDt

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DtLatestResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
): Parcelable