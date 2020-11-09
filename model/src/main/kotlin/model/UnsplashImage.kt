package model

import kotlinx.serialization.Serializable

@Serializable()
data class UnsplashImage(
    val id: String = "",
    val created_at: String = "",
    val width: Int = 0,
    val height: Int = 0,
    val color: String? = "#000000",
    val likes: Int = 0,
    val description: String? = "",
    val urls: UnsplashUrls = UnsplashUrls(),
    val links: UnsplashLinks = UnsplashLinks(),
    val user: UnsplashUser = UnsplashUser()
)

@Serializable
data class SearchResponse(
    val total: Int = 0,
    val total_pages: Int = 0,
    val results: List<UnsplashImage> = listOf()
)