package data

import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import model.SearchResponse
import model.UnsplashImage
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Response
import org.jetbrains.skija.Image
import java.io.File


object ClientApi {


    private const val BASE_URL = "https://api.unsplash.com"


    // Create a disk cache with "10 MB" size
    var cacheSize = 100 * 1024 * 1024
    var cache = Cache(File(".httpcache"), cacheSize.toLong())

    // Don't limit concurrent network requests by host.
    private val dispatcher = Dispatcher().apply { maxRequestsPerHost = maxRequests }

    private val client = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json { ignoreUnknownKeys = true })
        }


        install(Logging) {
            level = LogLevel.HEADERS
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }

        engine {

            clientCacheSize = cacheSize


            preconfigured = OkHttpClient.Builder()
                .cache(cache)
                .dispatcher(dispatcher)
                .build()

            addNetworkInterceptor {
                val response: Response = it.proceed(it.request())
                response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "max-age=31536000,public")
                    .build()
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

    suspend fun downloadImage(url: String): ImageAsset? {
        val call = client.request<HttpResponse> {
            url(url)
            method = HttpMethod.Get
        }


        return if (call.status.isSuccess()) {
            println("Success response on $url")
            Image.makeFromEncoded(call.content.toByteArray()).asImageAsset()
        } else {
            println("Failed response on $url")
            null
        }

    }


    // TODO
//    @GET
//    fun downloadPhoto(@Url url: String): Completable


    private fun HttpRequestBuilder.apiUrl(path: String) {

        // INSERT YOUR UNSPLASH API KEY
        val key = ""

        url(BASE_URL) {
            takeFrom(BASE_URL)
            encodedPath = path
        }
        parameter("client_id", key)
    }
}
