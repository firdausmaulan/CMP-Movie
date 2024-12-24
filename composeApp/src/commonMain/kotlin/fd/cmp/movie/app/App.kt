package fd.cmp.movie.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import fd.cmp.movie.screen.detail.MovieDetailScreen
import fd.cmp.movie.screen.list.MovieListScreen
import fd.cmp.movie.screen.location.LocationScreen
import fd.cmp.movie.screen.login.UserLoginScreen
import fd.cmp.movie.screen.user.UserScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) lightColorScheme() else lightColorScheme()
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = Route.Login) {
                composable<Route.Login> {
                    UserLoginScreen(navigateToMovieList = {
                        navController.navigate(Route.MovieList) {
                            popUpTo(Route.Login) { inclusive = true }
                        }
                    })
                }
                composable<Route.MovieList> {
                    MovieListScreen(
                        navigateToDetails = { objectId, genres ->
                            navController.navigate(Route.MovieDetail(objectId, genres))
                        },
                        navigateToUser = {
                            navController.navigate(Route.User)
                        }
                    )
                }
                composable<Route.MovieDetail> { backStackEntry ->
                    MovieDetailScreen(
                        movieId = backStackEntry.toRoute<Route.MovieDetail>().objectId,
                        genres = backStackEntry.toRoute<Route.MovieDetail>().genres,
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
                composable<Route.User> { backStackEntry ->
                    // Retrieve result from saved state handle when returning
                    val locationResult = backStackEntry.savedStateHandle.get<String?>(Keys.LOCATION_RESULT)
                    UserScreen(
                        locationResult = locationResult,
                        onEditLocation = {
                            navController.navigate(Route.Location)
                        },
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onLogout = {
                            navController.navigate(Route.Login) {
                                popUpTo(Route.MovieList) { inclusive = true }
                            }
                        }
                    )
                }
                composable<Route.Location> {
                    LocationScreen(
                        onClose = {
                            navController.popBackStack()
                        },
                        onConfirm = { result ->
                            // Save the result before popping back
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set(Keys.LOCATION_RESULT, result)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}