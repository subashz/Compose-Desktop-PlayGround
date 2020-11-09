import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import data.ClientApi
import org.jetbrains.skija.Image
import java.io.File


@Composable()
fun HttpImage(data: String, modifier: Modifier = Modifier) {

    val image = remember { mutableStateOf(imageFromResource("placeholder.jpg"))}

    Image(
        image.value,
        contentScale = ContentScale.Fit,
        modifier = modifier
    )

    LaunchedEffect(data) {
        try {
            if(data.isNotEmpty()) {
                val im = ClientApi.downloadImage(data)
                if (im != null) {
                    image.value = im
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


}

fun imageFromFile(file: File): ImageAsset {
    return Image.makeFromEncoded(file.readBytes()).asImageAsset()
}

