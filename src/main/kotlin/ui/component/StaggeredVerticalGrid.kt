package ui.component

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import kotlin.math.ceil

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    children: @Composable () -> Unit
) {
    Layout(
        children = children,
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}

@Composable
fun <T> GridView(
    cols: Int = 1,
    list: List<T>,
    rowModifier: Modifier = Modifier,
    colModifier: Modifier = Modifier,
    child: @Composable (dataModal: T) -> Unit
) {
    val rows = (list.size / cols) + (if (list.size % cols > 0) 1 else 0)
    ScrollableColumn(modifier = colModifier) {
        for (r in 0 until rows) {
            Row(modifier = rowModifier, horizontalArrangement = Arrangement.SpaceAround) {
                for (cell in 0 until cols) {
                    val i = (r * cols) + cell
                    if (i < list.size) {
                        child(list[i])
                        println("Composing child $i")
                    } else {
                        break
                    }
                }
            }
        }
    }
}
