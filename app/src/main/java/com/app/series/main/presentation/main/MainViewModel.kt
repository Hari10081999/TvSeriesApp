package com.app.series.main.presentation.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.series.main.data.remote.api.SeriesApi.Companion.API_KEY
import com.app.series.main.domain.models.Series
import com.app.series.main.domain.repository.SeriesRepository
import com.app.series.util.Constants
import com.app.series.util.Constants.POPULAR
import com.app.series.util.Constants.TV
import com.app.series.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val seriesRepository: SeriesRepository) : ViewModel() {


    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState = _mainUiState.asStateFlow()

    val showSplashScreen = mutableStateOf(true)

    init {
        load()
        viewModelScope.launch {
            delay(500)
            showSplashScreen.value = false
        }
    }

    private fun load(fetchFromRemote: Boolean = false) {
        loadPopularTvSeries(fetchFromRemote)
    }


    fun onEvent(event: MainUiEvents) {
        when (event) {

            is MainUiEvents.Refresh -> {

                _mainUiState.update {
                    it.copy(
                        isLoading = true
                    )
                }

                when (event.type) {
                    Constants.tvSeriesScreen -> {
                        loadPopularTvSeries(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                    }
                }
            }

            is MainUiEvents.OnPaginate -> {

                when (event.type) {
                    Constants.tvSeriesScreen -> {
                        loadPopularTvSeries(true)
                    }
                }
            }
        }
    }


    private fun loadPopularTvSeries(
        fetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {

        viewModelScope.launch {


            seriesRepository
                .getMoviesAndTvSeriesList(
                    fetchFromRemote,
                    isRefresh,
                    TV,
                    POPULAR,
                    mainUiState.value.popularTvSeriesPage
                )
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { mediaList ->

                                val shuffledMediaList = mediaList.toMutableList()
                                shuffledMediaList.shuffle()


                                if (isRefresh) {
                                    _mainUiState.update {
                                        it.copy(
                                            popularTvSeriesList = shuffledMediaList.toList(),
                                            popularTvSeriesPage = 1
                                        )
                                    }
                                } else {
                                    _mainUiState.update {
                                        it.copy(
                                            popularTvSeriesList =
                                            mainUiState.value.popularTvSeriesList + shuffledMediaList.toList(),
                                            popularTvSeriesPage = mainUiState.value.popularTvSeriesPage + 1
                                        )
                                    }
                                }


                                createTvSeriesList(
                                    seriesList = mediaList,
                                    isRefresh = isRefresh
                                )
                            }
                        }

                        is Resource.Error -> Unit

                        is Resource.Loading -> {
                            _mainUiState.update {
                                it.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun createTvSeriesList(
        seriesList: List<Series>,
        isRefresh: Boolean
    ) {

        val shuffledMediaList = seriesList.toMutableList()
        shuffledMediaList.shuffle()

        if (isRefresh) {
            _mainUiState.update {
                it.copy(
                    tvSeriesList = shuffledMediaList.toList()
                )
            }
        } else {
            _mainUiState.update {
                it.copy(
                    tvSeriesList = mainUiState.value.tvSeriesList + shuffledMediaList.toList()
                )
            }
        }
    }


}






