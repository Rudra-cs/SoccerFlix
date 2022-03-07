package com.rudra.flashgoalsadmin.data.model

data class Post(
    val category: String? = "",
    val title: String? = "",
    val description: String? = "",
    val createdAt: Long = 0L,
    val imageUrl: String? = ""

)

