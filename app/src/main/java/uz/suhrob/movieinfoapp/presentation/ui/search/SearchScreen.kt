package uz.suhrob.movieinfoapp.presentation.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.components.MovieSearchItem
import uz.suhrob.movieinfoapp.presentation.components.SearchField

@Composable
fun SearchScreen() {
    Scaffold(
        topBar = {
            SearchAppBar()
        }
    ) {
        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            items(
                listOf(
                    Movie(1, "Avengers", "", "", "", listOf(), "2019", true, 323324, 8.7)
                )
            ) { movie ->
                MovieSearchItem(movie = movie) {
                    // TODO()
                }
            }
        }
    }
}

@Composable
fun SearchAppBar() {
    TopAppBar(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
        Row(modifier = Modifier.fillMaxWidth().padding(end = 16.dp)) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .clickable(onClick = { /* TODO */ })
                    .padding(8.dp)
            )
            SearchField(value = "", onValueChange = { /*TODO*/ }, onSearch = { /*TODO*/ })
        }
    }
}