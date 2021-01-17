package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val focusRequester = FocusRequester()
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, tint = MaterialTheme.colors.onPrimary)
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.clickable(
                        onClick = {
                            focusRequester.requestFocus()
                            onValueChange("")
                        }
                    )
                )
            }
        },
        placeholder = {
            Text(text = "Search", color = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f))
        },
        modifier = modifier.fillMaxWidth().focusRequester(focusRequester),
        backgroundColor = Color.Transparent,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        onImeActionPerformed = { action, _ ->
            if (action == ImeAction.Search) {
                onSearch()
            }
        },
        singleLine = true,
        activeColor = MaterialTheme.colors.onPrimary,
    )
}