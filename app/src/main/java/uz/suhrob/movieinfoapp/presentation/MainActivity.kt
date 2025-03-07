package uz.suhrob.movieinfoapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.room.Room
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import uz.suhrob.movieinfoapp.data.local.DATABASE_NAME
import uz.suhrob.movieinfoapp.data.local.MovieDatabase
import uz.suhrob.movieinfoapp.data.pref.MovieInfoDataStore
import uz.suhrob.movieinfoapp.data.pref.MovieInfoPref
import uz.suhrob.movieinfoapp.presentation.theme.MovieInfoAppTheme
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsScreen
import uz.suhrob.movieinfoapp.presentation.ui.home.HomeScreen
import uz.suhrob.movieinfoapp.presentation.ui.root.Root

@ExperimentalAnimationApi
@ExperimentalSerializationApi
@ExperimentalFoundationApi
class MainActivity : AppCompatActivity() {

    private lateinit var database: MovieDatabase
    private lateinit var movieInfoDataStore: MovieInfoPref

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (!::database.isInitialized) {
            database = Room.databaseBuilder(
                applicationContext,
                MovieDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
        if (!::movieInfoDataStore.isInitialized) {
            movieInfoDataStore = MovieInfoDataStore(applicationContext)
        }
        val rootComponent = get<Root>(parameters = { parametersOf(defaultComponentContext()) })
        setContent {
            MovieInfoApp(rootComponent)
        }
    }
}

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalCoroutinesApi::class,
    ExperimentalDecomposeApi::class
)
@Composable
fun MovieInfoApp(component: Root) {
    MovieInfoAppTheme {
        val childStack by component.childStack.subscribeAsState()
        Children(stack = childStack, animation = stackAnimation(slide())) {
            when (val child = it.instance) {
                is Root.Child.DetailsChild -> DetailsScreen(component = child.component)
                is Root.Child.HomeChild -> HomeScreen(home = child.component)
            }
        }
    }
}