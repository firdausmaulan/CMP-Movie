package fd.cmp.movie

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import fd.cmp.movie.screen.detail.MovieDetailScreen
import fd.cmp.movie.screen.list.MovieListScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
object ListDestination

@Serializable
data class DetailDestination(val objectId: Int?, val genres : String?)

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) lightColorScheme() else lightColorScheme()
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = ListDestination) {
                composable<ListDestination> {
                    MovieListScreen(navigateToDetails = { objectId, genres ->
                        navController.navigate(DetailDestination(objectId, genres))
                    })
                }
                composable<DetailDestination> { backStackEntry ->
                    MovieDetailScreen(
                        movieId = backStackEntry.toRoute<DetailDestination>().objectId,
                        genres = backStackEntry.toRoute<DetailDestination>().genres,
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}