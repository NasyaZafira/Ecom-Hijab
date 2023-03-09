package com.fitri.jilbab.data.model.admin

data class CategoryListResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)