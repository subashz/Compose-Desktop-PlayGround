package ui

import HttpImage
import androidx.compose.desktop.Window
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.input.key.ExperimentalKeyInput
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.plus
import androidx.compose.ui.input.key.shortcuts
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import imageloader.ImageLoader
import me.subash.common.data.ClientApi
import me.subash.common.data.UnsplashImage
import ui.component.StaggeredVerticalGrid
import util.Utils
import java.awt.Desktop
import java.io.IOException
import java.net.URI


data class WallpaperModel(val id: Int, val url: String)


val topBgColor = 0xFF1F2025
val gridBgColor = 0xFF191A1F
val searchBgColor = 0xFF41444D
val searchIconTintColor = 0xFF5C5F68
val detailBgColor = 0xFF0E0E10
val dlBlueColor = 0xFF0085FE
val dlGrayColor = 0xFF1F2025
val optionBlueColor = 0xFF0183FB
val optionGrayColor = 0xFF3F424B

@ExperimentalKeyInput
fun WallpaperApp() = Window(
    title = "Wallpaer App",
    undecorated = false,
    centered = true,
    size = IntSize(1200, 900)
) {

    val imageUrl =
        "https://images.unsplash.com/photo-1579546929662-711aa81148cf?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80"

    val coroutineScope = rememberCoroutineScope()
    val wallpaper = remember { mutableStateListOf<UnsplashImage>() }

    val query = remember { mutableStateOf("abstract") }


    val page = remember { mutableStateOf(1) }
    val loadNewData = remember { mutableStateOf(true) }

    val desktopWallpaperPath = remember { mutableStateOf("") }

    LaunchedEffect(loadNewData.value) {
        if (loadNewData.value && query.value.isNotEmpty()) {
//            val data = ClientApi.getImage(page = page.value + 1)
            val data = ClientApi.searchImage(query = query.value, page = page.value + 1).results
            page.value = page.value + 1
            wallpaper.addAll(data)
            loadNewData.value = false
        }

    }

    LaunchedEffect(desktopWallpaperPath.value) {
        if (desktopWallpaperPath.value.isNotEmpty()) {
            setWallpaper(desktopWallpaperPath.value)
        }
    }


    val toolbarHeight = 48.dp

    MaterialTheme {

        Surface(elevation = 8.dp) {

            Row {

                val selectedImage = remember { mutableStateOf(UnsplashImage()) }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .background(Color(topBgColor))
                ) {

                    Spacer(
                        Modifier
                            .preferredHeight(16.dp)
                            .fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier
                            .preferredHeight(toolbarHeight)
                            .padding(horizontal = 16.dp)

                    ) {


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(searchBgColor))
                                .weight(1f)

                        ) {

                            Icon(
                                asset = Icons.Outlined.Search,
                                tint = Color.Gray,
                                modifier = Modifier
                                    .clickable {
                                        wallpaper.clear()
                                        page.value = 1
                                        loadNewData.value = true
                                    }
                                    .padding(horizontal = 8.dp)
                            )

                            TextField(
                                value = query.value,
                                placeholder = {
                                    Text("Search Images", color = Color.White)
                                },
                                onValueChange = {
                                    query.value = it
                                    println("Value is $it")
                                },
                                activeColor = Color(optionBlueColor),
                                textStyle = TextStyle(color = Color.White, fontSize = 14.sp),

                                modifier = Modifier
                                    .weight(1f)
                                    .shortcuts {
                                        on(Key.MetaLeft + Key.ShiftLeft + Key.Enter) {
                                            query.value = "Cleared with shift!"
                                        }
                                        on(Key.Enter) {
                                            wallpaper.clear()
                                            page.value = 1
                                            loadNewData.value = true
                                        }
                                    }
                            )

                            Text(
                                text = "16,350 photos",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                            )

                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .preferredSize(24.dp)
                            ) {
                                Icon(
                                    asset = Icons.Outlined.Close,
                                    tint = Color.DarkGray,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(Color.Gray)
                                        .padding(4.dp)
                                )
                            }


                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Red)
                                .fillMaxHeight()

                        ) {

                            val photoOptionToggle = remember { mutableStateOf(true) }
                            Box(
                                alignment = Alignment.Center,
                                modifier = Modifier
                                    .background(Color(if (photoOptionToggle.value) optionBlueColor else optionGrayColor))
                                    .fillMaxHeight()
                                    .clickable { photoOptionToggle.value = true }
                                    .padding(horizontal = 8.dp)


                            ) {

                                Text(
                                    text = "Photos",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 14.sp
                                )
                            }

                            Box(
                                alignment = Alignment.Center,
                                modifier = Modifier
                                    .background(Color(if (photoOptionToggle.value) optionGrayColor else optionBlueColor))
                                    .fillMaxHeight()
                                    .clickable { photoOptionToggle.value = false }
                                    .padding(horizontal = 8.dp)


                            ) {

                                Text(
                                    text = "Collection",
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 14.sp
                                )
                            }


                        }

                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(searchBgColor))
                                .preferredWidth(38.dp)
                                .fillMaxHeight()
                                .padding(8.dp)
                                .align(Alignment.CenterVertically)

                        ) {
                            Icon(
                                asset = Icons.Filled.Settings,
                                tint = Color.Gray
                            )
                        }

                    }

                    Spacer(modifier = Modifier.preferredHeight(16.dp))


                    Box(modifier = Modifier) {

                        val stateVertical = rememberScrollState(0f)

                        if (stateVertical.value == stateVertical.maxValue) {
                            println("Max scrolled")
                            loadNewData.value = true
                        }

                        ScrollableColumn(
                            modifier = Modifier.background(Color(gridBgColor)).padding(end = 12.dp),
                            scrollState = stateVertical
                        ) {
                            StaggeredVerticalGrid(
                                maxColumnWidth = 200.dp,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                wallpaper.forEach { data ->
                                    data.urls.thumb.let { it1 ->
                                        RoundedImage(
                                            data = it1,
                                            selectedImage,
                                            modifier = Modifier
                                                .clickable {
                                                    selectedImage.value = data
                                                    println("Selected $data")
                                                }
                                                .padding(8.dp)
                                        )
                                    }
                                }
                            }

                        }

                        VerticalScrollbar(
                            modifier = Modifier.align(Alignment.CenterEnd)
                                .fillMaxHeight(),
                            adapter = rememberScrollbarAdapter(stateVertical)
                        )

                    }


                }

                Column(
                    modifier = Modifier
                        .background(Color(detailBgColor))
                        .fillMaxHeight()
                        .fillMaxWidth()

                ) {

                    RoundedImage(
                        data = selectedImage.value.urls.regular,
                        selectedImage = selectedImage,
                        modifier = Modifier.padding(16.dp).fillMaxWidth()
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {

                        HttpImage(
                            data = selectedImage.value.user.profile_image.small,
                            modifier = Modifier
                                .clip(CircleShape)
                                .preferredSize(36.dp)
                        )

                        Text(
                            text = selectedImage.value.user.name,
                            color = Color.White,
                            modifier = Modifier.padding(start = 16.dp).weight(1f)
                        )


                        Icon(
                            asset = Icons.Outlined.Favorite,
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(horizontal = 8.dp).preferredSize(20.dp)
                        )


                        Text(
                            text = selectedImage.value.likes.toString(),
                            color = Color.White,
                            modifier = Modifier.padding(start = 2.dp)
                        )

                    }

                    ActionButton(Icons.Outlined.GetApp, "Download HD photo", true) {
                        // TODO
                    }

                    ActionButton(Icons.Filled.ContentCopy, "Copy photo") {

                    }
                    ActionButton(Icons.Outlined.PersonalVideo, "Set as wallpaper") {
                        desktopWallpaperPath.value = selectedImage.value.urls.full
                    }

                    ActionButton(Icons.Outlined.OpenInBrowser, "View on Unsplash"){

                        Utils.openURL(selectedImage.value.links.html)

                    }


                }


            }


        }


    }


}


@Composable
fun RoundedImage(data: String, selectedImage: MutableState<UnsplashImage>, modifier: Modifier = Modifier) {

    val showBorder = data == selectedImage.value.urls.thumb

    HttpImage(
        data = data,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .border(
                4.dp,
                color = if (showBorder) Color(optionBlueColor) else Color.Transparent,
                RoundedCornerShape(4.dp)
            )

    )


}

@Composable
fun ActionButton(
    imageAsset: VectorAsset,
    title: String,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    listener: (String) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(top = 16.dp, bottom = 8.dp, end = 16.dp, start = 16.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (isSelected) Color(dlBlueColor) else Color(dlGrayColor))
            .clickable { listener(title) }
            .padding(8.dp)
    ) {

        Icon(
            asset = imageAsset,
            tint = (if (isSelected) Color.White else Color(searchIconTintColor))
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
                .wrapContentWidth(Alignment.CenterHorizontally),
            color = Color.White

        )

    }

}


suspend fun setWallpaper(path: String) {
    val data = ImageLoader.getCachedAbsolute(path)
    try {
        val process = Runtime.getRuntime().exec("feh --bg-fill $path");
    } catch (e: IOException) {
        e.printStackTrace();
    }


}

