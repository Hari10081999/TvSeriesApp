package com.app.series.main.presentation.main

import com.app.series.main.domain.models.Genre
import com.app.series.main.domain.models.Series

data class MainUiState(

    val popularTvSeriesPage: Int = 1,

    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val areListsToBuildSpecialListEmpty: Boolean = true,

    val popularTvSeriesList: List<Series> = emptyList(),

    // popularTvSeriesList + topRatedTvSeriesList
    val tvSeriesList: List<Series> = emptyList(),

    val recommendedAllList: List<Series> = emptyList(),

    // matching items in:
    // recommendedAllList and trendingAllList
    val specialList: List<Series> = emptyList(),

    val moviesGenresList: List<Genre> = emptyList(),
    val tvGenresList: List<Genre> = emptyList(),


    )