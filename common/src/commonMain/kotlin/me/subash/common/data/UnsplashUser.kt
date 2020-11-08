package me.subash.common.data

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashUser(
    val id: String="",
    val username: String="",
    val name: String="",
    val portfolio_url: String?="",
    val bio: String?="",
    val location: String?="",
    val total_likes: Int=0,
    val total_photos: Int=0,
    val total_collection: Int=0,
    val profile_image: UnsplashUrls= UnsplashUrls(),
    val links: UnsplashLinks=UnsplashLinks()
)