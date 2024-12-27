package fd.cmp.movie.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import fd.cmp.movie.helper.ColorHelper
import fd.cmp.movie.screen.detail.MovieDetailScreen
import fd.cmp.movie.screen.list.MovieListScreen
import fd.cmp.movie.screen.location.LocationScreen
import fd.cmp.movie.screen.login.UserLoginScreen
import fd.cmp.movie.screen.photo.PhotoScreen
import fd.cmp.movie.screen.user.UserScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme())
            darkColorScheme(
                primary = ColorHelper.primary,
                onPrimary = ColorHelper.onPrimary,
                secondary = ColorHelper.secondary,
                onSecondary = ColorHelper.onSecondary,
                tertiary = ColorHelper.tertiary,
                onTertiary = ColorHelper.onTertiary,
                background = ColorHelper.background,
                onBackground = ColorHelper.onBackground,
                surface = ColorHelper.surface,
                onSurface = ColorHelper.onSurface,
                error = ColorHelper.error,
                onError = ColorHelper.onError
            )
        else
            lightColorScheme(
                primary = ColorHelper.primary,
                onPrimary = ColorHelper.onPrimary,
                secondary = ColorHelper.secondary,
                onSecondary = ColorHelper.onSecondary,
                tertiary = ColorHelper.tertiary,
                onTertiary = ColorHelper.onTertiary,
                background = ColorHelper.background,
                onBackground = ColorHelper.onBackground,
                surface = ColorHelper.surface,
                onSurface = ColorHelper.onSurface,
                error = ColorHelper.error,
                onError = ColorHelper.onError
            )
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
                        navigateToDetails = { movieId ->
                            navController.navigate(Route.MovieDetail(movieId))
                        },
                        navigateToUser = {
                            navController.navigate(Route.User)
                        }
                    )
                }
                composable<Route.MovieDetail> { backStackEntry ->
                    MovieDetailScreen(
                        movieId = backStackEntry.toRoute<Route.MovieDetail>().movieId,
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
                composable<Route.User> { backStackEntry ->
                    // Retrieve result from saved state handle when returning
                    val locationResult = backStackEntry.savedStateHandle.get<String?>(Keys.LOCATION_RESULT)
                    val imageResult = backStackEntry.savedStateHandle.get<String?>(Keys.IMAGE_RESULT)
                    UserScreen(
                        locationResult = locationResult,
                        imageResult = imageResult,
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onEditPhoto = {
                            navController.navigate(Route.Photo)
                        },
                        onEditLocation = {
                            navController.navigate(Route.Location(it))
                        },
                        onLogout = {
                            navController.navigate(Route.Login) {
                                popUpTo(Route.MovieList) { inclusive = true }
                            }
                        }
                    )
                }
                composable<Route.Location> { backStackEntry ->
                    LocationScreen(
                        locationData = backStackEntry.toRoute<Route.Location>().locationData,
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
                composable<Route.Photo> {
                    PhotoScreen(
                        onClose = {
                            navController.popBackStack()
                        },
                        onSuccessUpload = { result ->
                            // Save the result before popping back
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set(Keys.IMAGE_RESULT, result)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}