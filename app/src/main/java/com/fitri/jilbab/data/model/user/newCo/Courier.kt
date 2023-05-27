package com.fitri.jilbab.data.model.user.newCo
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Courier(
    val jne: List<Jne>,
    val pos: List<Po>,
    val tiki: List<Tiki>
): Parcelable