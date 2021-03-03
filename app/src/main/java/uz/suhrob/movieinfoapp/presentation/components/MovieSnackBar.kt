package uz.suhrob.movieinfoapp.presentation.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun MovieSnackBar(
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit
) {
    SnackbarHost(
        hostState = scaffoldState.snackbarHostState,
        snackbar = { data ->
            Snackbar(
                action = {
                    data.actionLabel?.let { label ->
                        TextButton(onClick = onClickAction) {
                            Text(text = label)
                        }
                    }
                },
                content = {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.body2,
                        color = Color.White
                    )
                }
            )
        },
        modifier = modifier
    )
}

class MovieSnackBarController(
    private val scope: CoroutineScope
) {
    private var snackBarJob: Job? = null

    fun getScope() = scope

    fun showSnackBar(
        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String? = null
    ) {
        snackBarJob?.cancel()
        snackBarJob = scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(message, actionLabel)
        }
    }
}