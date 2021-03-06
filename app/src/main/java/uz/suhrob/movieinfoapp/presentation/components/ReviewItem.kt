package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReviewItem(
    authorName: String,
    avatarUrl: String,
    content: String
) {
    val textCollapsed = mutableStateOf(false)
    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarImage(path = avatarUrl, modifier = Modifier.size(48.dp))
                Text(text = authorName, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { textCollapsed.value = !textCollapsed.value }) {
                    Icon(
                        imageVector = if (textCollapsed.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
            Divider()
            Text(
                text = if (textCollapsed.value) content else getFirstSentence(content),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

fun getFirstSentence(string: String): String {
    return string.split(".")[0] + "."
}