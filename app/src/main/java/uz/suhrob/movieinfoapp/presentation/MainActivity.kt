package uz.suhrob.movieinfoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.theme.MovieInfoAppTheme
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsScreen
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchScreen

@ExperimentalFoundationApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieInfoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    HomeScreen()
//                    DetailsScreen(movie = Movie(1, "Avengers", "Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet. ", "", "", listOf("Action", "Science Fiction", "Science Fiction", "Science Fiction", "Science Fiction"), "2019", true, 323324, 8.7))
                    SearchScreen()
                }
            }
        }
    }
}