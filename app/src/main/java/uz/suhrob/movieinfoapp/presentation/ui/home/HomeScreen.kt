package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.components.Category
import uz.suhrob.movieinfoapp.presentation.components.CategoryRow
import uz.suhrob.movieinfoapp.presentation.components.GenreRow
import uz.suhrob.movieinfoapp.presentation.components.MovieGrid

@ExperimentalFoundationApi
@Composable
fun HomeScreen() {
    Scaffold {
        Column(modifier = Modifier.fillMaxSize()) {
            val selectedCategory = mutableStateOf(Category.POPULAR)
            CategoryRow(selectedCategory.value) { category ->
                selectedCategory.value = category
            }
            val genres = listOf(
                Genre(1, "Action"),
                Genre(1, "Crime"),
                Genre(1, "Comedy"),
                Genre(1, "Animation")
            )
            val selectedGenre = mutableStateOf(genres[1])
            GenreRow(genres = genres, selectedGenre = selectedGenre.value) { genre ->

            }
            val movies = listOf(
                Movie(1, "Avengers", "", "", "", genres.map { it.name }, "", true, 1, 1.0),
                Movie(1, "Avengers", "", "", "", genres.map { it.name }, "", true, 1, 1.0),
                Movie(1, "Avengers", "", "", "", genres.map { it.name }, "", true, 1, 1.0),
                Movie(1, "Avengers", "", "", "", genres.map { it.name }, "", true, 1, 1.0),
                Movie(1, "Avengers", "", "", "", genres.map { it.name }, "", true, 1, 1.0),
                Movie(1, "Avengers", "", "", "", genres.map { it.name }, "", true, 1, 1.0),
                Movie(1, "Avengers", "", "", "", genres.map { it.name }, "", true, 1, 1.0),
            )
            MovieGrid(movies = movies, onMovieClicked = {})
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}