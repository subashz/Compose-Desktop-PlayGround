package me.subash.common.data

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.Unauthorized

object ClientApi {

    val endpoint = "https://api.unsplash.com"

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json { ignoreUnknownKeys = true })
        }

//        install(Logging) {
//            logger = Logger.DEFAULT
//            level = LogLevel.BODY
//        }

        HttpResponseValidator {
            validateResponse {

//                when (it.status) {
//                    HttpStatusCode.Unauthorized -> throw Unauthorized()
//                }
//
//                if (!it.status.isSuccess()) {
//                    when (it.call.request.url.encodedPath) {
//                        "/favorites" -> throw CannotFavorite()
//                        else -> error("Bad status: ${it.status}")
//                    }
//                }
            }
        }
    }

    suspend fun getImage(page: Int = 1, perPage: Int = 100): List<UnsplashImage> = client.get {
        apiUrl("photos")
        parameter("page", page)
        parameter("per_page", perPage)
    }

    suspend fun searchImage(query: String, page: Int = 1, perPage: Int = 100): SearchResponse = client.get {
        apiUrl("search/photos")
        parameter("query", query)
        parameter("page", page)
        parameter("per_page", perPage)
    }

    suspend fun markAsDownloaded(id: Int): Unit = client.get {
        apiUrl("photos/$id/download")
    }


//    UnsplashPhotoPicker.init(
//    (requireActivity()).applicationContext as Application, // application
//    /* optional page size */
//    ).setLoggingEnabled(true)


//    @GET("/photos")
//    suspend fun loadPhotos(
//        @Query("client_id") clientId: String,
//        @Query("page") page: Int,
//        @Query("per_page") pageSize: Int
//    ): Observable<Response<List<UnsplashPhoto>>>
//
//    @GET("search/photos")
//    fun searchPhotos(
//        @Query("client_id") clientId: String,
//        @Query("query") criteria: String,
//        @Query("page") page: Int,
//        @Query("per_page") pageSize: Int
//    ): Observable<Response<SearchResponse>>
//
//    @GET
//    fun downloadPhoto(@Url url: String): Completable

    private fun HttpRequestBuilder.apiUrl(path: String) {

//        if (userId != null) {
//            header(HttpHeaders.Authorization, "Bearer $userId")
//        }

        val key = "8u-M1rysEt3wGbEDYcwIjjD3gnWmET1A_eQz9273UL8"

        header(HttpHeaders.CacheControl, "no-cache")
        url {
            takeFrom(endpoint)
            encodedPath = path
        }
        parameter("client_id", "$key")
    }
}