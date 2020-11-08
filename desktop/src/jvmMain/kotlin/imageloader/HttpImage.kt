import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import imageloader.ImageLoader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.skija.Image
import java.io.File
import kotlin.coroutines.CoroutineContext


@Composable()
fun HttpImage(data: String, modifier: Modifier = Modifier) {

    val imageLoader = ImageLoader

    val image = remember { mutableStateOf(imageFromResource("loading.gif"))}

    val coroutineScope = rememberCoroutineScope()

    Image(
        image.value,
        contentScale = ContentScale.Fit,
        modifier = modifier
    )

    LaunchedEffect(data) {
        try {
            val imagePath = imageLoader.getImage(data)
            image.value = imageFromFile(File(imagePath))
            println("Loading from file: $imagePath")
        } catch (e: java.lang.Exception) {

        }
    }


}

fun imageFromFile(file: File): ImageAsset {
    return Image.makeFromEncoded(file.readBytes()).asImageAsset()
}

