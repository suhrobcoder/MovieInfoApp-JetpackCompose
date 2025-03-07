package uz.suhrob.movieinfoapp.presentation.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.ui.details.Details
import uz.suhrob.movieinfoapp.presentation.ui.home.Home

class RootComponent(
    componentContext: ComponentContext,
) : Root, ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.Home) },
        childFactory = ::child,
        handleBackButton = true,
    )
    override val childStack: Value<ChildStack<*, Root.Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext): Root.Child {
        return when (config) {
            is Config.Details -> Root.Child.DetailsChild(get<Details>(parameters = { parametersOf(config.movie, { navigation.pop() }) }))
            Config.Home -> {
                val navigateToDetails: (Movie) -> Unit = { navigation.push(Config.Details(it)) }
                Root.Child.HomeChild(get<Home>(parameters = { parametersOf(componentContext, navigateToDetails) }))
            }
        }
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        data object Home : Config

        @Parcelize
        class Details(val movie: Movie) : Config
    }
}