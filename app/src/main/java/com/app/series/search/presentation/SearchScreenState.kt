package com.app.series.search.presentation

import com.app.series.main.domain.models.Series

data class SearchScreenState(

    val searchPage: Int = 1,

    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,

    val searchQuery: String = "",

    val searchList: List<Series> = emptyList(),


    )