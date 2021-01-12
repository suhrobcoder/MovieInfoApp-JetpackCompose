package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search)
        },
        placeholder = {
            Text(text = "Search")
        },
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.Transparent,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        onImeActionPerformed = { action, _ ->
            if (action == ImeAction.Search) {
                onSearch()
            }
        }
    )
}