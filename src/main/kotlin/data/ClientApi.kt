package data

import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import model.SearchResponse
import model.UnsplashImage
import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import okhttp3.*
import okhttp3.CacheControl
import org.jetbrains.skija.Image
import java.io.File
import java.util.concurrent.TimeUnit


object ClientApi {

    private const val BASE_URL = "https://api.unsplash.com"


    var cacheSize = 10 * 1024 * 1024
    var cache = Cache(File(".httpcache"), cacheSize.toLong())

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

            clientCacheSize = 300

            addInterceptor { chain ->
                val builder: Request.Builder = chain.request().newBuilder()
                builder.cacheControl(CacheControl.FORCE_NETWORK)
                chain.proceed(builder.build())
            }

            preconfigured = OkHttpClient.Builder().cache(cache).build()

            addNetworkInterceptor {
                val response: Response = it.proceed(it.request())

                val cacheControl: CacheControl = CacheControl.Builder()
                    .maxAge(24, TimeUnit.HOURS) // 15 minutes cache
                    .build()

                response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheControl.toString())
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

        if (call.status.isSuccess()) {
            println("Success response on $url")
            return Image.makeFromEncoded(call.content.toByteArray()).asImageAsset()
        } else {
            println("Failed response on $url")
            return null
        }

    }


    // TODO
//    @GET
//    fun downloadPhoto(@Url url: String): Completable

    private fun HttpRequestBuilder.apiUrl(path: String) {


        // INSERT YOUR UNSPLASH API KEY
        val key = "M1rysEt3wGbEDYcwIjjD3gnWmET1A_eQz9273UL8_2x"

        url(BASE_URL) {
            takeFrom(BASE_URL)
            encodedPath = path
        }
        parameter("client_id", "$key")
    }
}
