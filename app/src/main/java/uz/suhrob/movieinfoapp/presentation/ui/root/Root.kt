package uz.suhrob.movieinfoapp.presentation.ui.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import uz.suhrob.movieinfoapp.presentation.ui.details.Details
import uz.suhrob.movieinfoapp.presentation.ui.home.Home

interface Root {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class HomeChild(val component: Home) : Child
        class DetailsChild(val component: Details) : Child
    }
}