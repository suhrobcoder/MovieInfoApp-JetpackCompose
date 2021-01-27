package uz.suhrob.movieinfoapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.R

const val MAX_RATING = 10

@Composable
fun RatingBar(
    value: Int,
    modifier: Modifier = Modifier,
    onChange: (Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(vertical = 16.dp)) {
        Row {
            for (i in 1..MAX_RATING) {
                StarImageButton(isFilled = i <= value, onClick = { onChange(i) }, modifier = Modifier.weight(1f))
            }
        }
        Text(text = "$value/$MAX_RATING")
    }
}

@Composable
fun StarImageButton(
    isFilled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Image(
        bitmap = imageResource(id = if (isFilled) R.drawable.ic_star_filled else R.drawable.ic_star_outlined),
        modifier = modifier.clickable(onClick = onClick).padding(all = 4.dp)
    )
}