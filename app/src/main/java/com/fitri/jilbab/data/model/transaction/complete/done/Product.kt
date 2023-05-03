package com.fitri.jilbab.data.model.transaction.complete.done
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val product: ProductX,
    val qty: Int
): Parcelable