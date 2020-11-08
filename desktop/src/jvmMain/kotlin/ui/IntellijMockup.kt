import androidx.compose.desktop.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun IntellijMockup() = Window(
    size = IntSize(950, 650),
    centered = true
) {
    var text by remember { mutableStateOf("Hello, World!") }



    MaterialTheme {

        Box(modifier = Modifier) {

            Text(
                text = "2",
                fontSize = 10.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(Color.Yellow)
                    .padding(4.dp)
                    .align(Alignment.BottomEnd)
            )

            Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.24f)
                        .fillMaxHeight()
                        .background(color = Color(0xFFF2F2F2))

                ) {

                    Spacer(
                        modifier = Modifier
                            .height(30.dp)
                    )

                    Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {

                        Image(
                            asset = imageResource("intellij_icon.png"),
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .height(40.dp)
                                .width(40.dp)
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = "IntelliJ IDEA",
                                fontSize = 14.sp
                            )
                            Text(
                                text = "2020.3 EAP (203.541923)",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(24.dp))


                    val selectedIndex = remember { mutableStateOf(0) }
                    val sideBarItems = listOf("Projects", "Customize", "Plugins")

                    sideBarItems.forEachIndexed { index, s ->
                        SideBarItem(index, s, selectedIndex)
                    }


                    Spacer(modifier = Modifier.weight(1f))

                    Row {
                        Text(
                            text = "Help",
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Icon(
                            asset = Icons.Outlined.KeyboardArrowDown,

                            modifier = Modifier
                                .drawOpacity(0.2f)
                                .align(Alignment.CenterVertically)
                        )
                    }


                }


                Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

                    Spacer(modifier = Modifier.size(120.dp))

                    Text(
                        text = " Welcome to IntelliJ IDEA",
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Text(
                        text = "Create a new project to start from scratch",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = "Open existing project from disk or version control",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.size(48.dp))


                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {

                        ActionItem("plus.png", "New Project", modifier = Modifier.align(Alignment.CenterVertically))
                        Spacer(modifier = Modifier.size(48.dp))
                        ActionItem("folder.png", "Open", modifier = Modifier.align(Alignment.CenterVertically))
                        Spacer(modifier = Modifier.size(48.dp))
                        ActionItem("git.png", "Get from VCS", modifier = Modifier.align(Alignment.CenterVertically))

                    }

                }


                Surface(elevation = 8.dp) {

                }

            }


        }


    }
}

@Composable
fun SideBarItem(index: Int, text: String, selectedIndex: MutableState<Int>) {

    val isSelected = index == selectedIndex.value

    Text(
        text = text,
        fontSize = 13.sp,
        color = if (!isSelected) Color.Black else Color.White,
        modifier = Modifier.fillMaxWidth()
            .clickable {
                selectedIndex.value = index
            }
            .background(if (!isSelected) Color.Transparent else Color(0xFF2675BF))
            .padding(horizontal = 16.dp, vertical = 8.dp)

    )

}

@Composable
fun ActionItem(imagePath: String, text: String, modifier: Modifier = Modifier) {

    Column(modifier = modifier) {

        Image(
            asset = imageResource("$imagePath"),
            colorFilter = ColorFilter.tint(Color(0xFF3996FB)),
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFDCEDFE))
                .padding(15.dp)
                .preferredHeight(32.dp)
                .preferredHeight(32.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 12.dp)

        )
    }
}