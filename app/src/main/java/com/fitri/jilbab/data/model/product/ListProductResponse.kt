package com.fitri.jilbab.data.model.product

data class ListProductResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)