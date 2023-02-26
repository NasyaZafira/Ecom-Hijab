package com.fitri.jilbab.data.model.profile.edit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditProfileResponse(
    @SerializedName("data")
    val `data`: Data,
    val message: String,
    val success: Boolean
) : Parcelable