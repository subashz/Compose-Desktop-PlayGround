package model

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashLinks(
    val self: String="",
    val html: String="",
    val photos: String?="",
    val likes: String?="",
    val portfolio: String?="",
)