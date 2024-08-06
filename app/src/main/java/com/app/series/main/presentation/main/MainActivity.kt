package com.app.series.main.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.series.main.presentation.popularAndTvSeries.SeriesListScreen
import com.app.series.series_details.presentation.details.MediaDetailScreen
import com.app.series.series_details.presentation.details.SeriesDetailsScreenEvents
import com.app.series.series_details.presentation.details.SeriesDetailsViewModel
import com.app.series.series_details.presentation.details.SomethingWentWrong
import com.app.series.search.presentation.SearchScreen
import com.app.series.ui.theme.TheMoviesTheme
import com.app.series.util.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TheMoviesTheme {

                val mainViewModel = hiltViewModel<MainViewModel>()
                val mainUiState = mainViewModel.mainUiState.collectAsState().value

                installSplashScreen().apply {
                    setKeepOnScreenCondition {
                        mainViewModel.showSplashScreen.value
                    }
                }

                Navigation(
                    mainUiState = mainUiState,
                    onEvent = mainViewModel::onEvent
                )

            }
        }

    }
}

@Composable
fun Navigation(
    mainUiState: MainUiState,
    onEvent: (MainUiEvents) -> Unit
) {
    val navController = rememberNavController()

    val seriesDetailsViewModel = hiltViewModel<SeriesDetailsViewModel>()
    val seriesDetailsScreenState = seriesDetailsViewModel.seriesDetailsScreenState.collectAsState().value


    NavHost(
        navController = navController,
        startDestination = Route.SERIES_LIST_SCREEN
    ) {

        composable(Route.SERIES_LIST_SCREEN) {
            SeriesListScreen(
                navController = navController,
                mainUiState = mainUiState,
                onEvent = onEvent
            )
        }

        composable(Route.SEARCH_SCREEN) {
            SearchScreen(
                navController = navController,
                mainUiState = mainUiState,
            )
        }

        composable(
            "${Route.SERIES_DETAILS_SCREEN}?id={id}&type={type}&category={category}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("type") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType }
            )
        ) {

            val id = it.arguments?.getInt("id") ?: 0
            val type = it.arguments?.getString("type") ?: ""
            val category = it.arguments?.getString("category") ?: ""

            LaunchedEffect(key1 = true) {
                seriesDetailsViewModel.onEvent(
                    SeriesDetailsScreenEvents.SetDataAndLoad(
                        moviesGenresList = mainUiState.moviesGenresList,
                        tvGenresList = mainUiState.tvGenresList,
                        id = id,
                        type = type,
                        category = category
                    )
                )
            }

            if (seriesDetailsScreenState.series != null) {
                MediaDetailScreen(
                    navController = navController,
                    series = seriesDetailsScreenState.series,
                    seriesDetailsScreenState = seriesDetailsScreenState,
                    onEvent = seriesDetailsViewModel::onEvent
                )
            } else {
                SomethingWentWrong()
            }
        }

    }
}













