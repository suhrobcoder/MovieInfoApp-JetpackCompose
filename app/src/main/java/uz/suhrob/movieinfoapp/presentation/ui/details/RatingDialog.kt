package uz.suhrob.movieinfoapp.presentation.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import uz.suhrob.movieinfoapp.presentation.components.RatingBar

@Composable
fun RatingDialog(
    rating: Int,
    onChange: (Int) -> Unit,
    onSubmit: () -> Unit,
    onClose: () -> Unit
) {
    Dialog(onDismissRequest = onClose) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(size = 16.dp))
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(all = 8.dp)
        ) {
            RatingBar(value = rating, onChange = { onChange(it) })
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onClose) {
                    Text(text = "CANCEL")
                }
                Spacer(modifier = Modifier.size(size = 8.dp))
                TextButton(onClick = onSubmit) {
                    Text(text = "OK")
                }
            }
        }
    }
}