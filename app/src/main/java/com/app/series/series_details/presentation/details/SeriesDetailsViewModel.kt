package com.app.series.series_details.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.series.main.data.remote.api.SeriesApi.Companion.API_KEY
import com.app.series.main.domain.repository.SeriesRepository
import com.app.series.series_details.domain.repository.DetailsRepository
import com.app.series.series_details.domain.usecase.MinutesToReadableTime
import com.app.series.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    private val mediaRepository: SeriesRepository,
    private val detailsRepository: DetailsRepository) : ViewModel() {

    private val _seriesDetailsScreenState = MutableStateFlow(SeriesDetailsScreenState())
    val seriesDetailsScreenState = _seriesDetailsScreenState.asStateFlow()

    fun onEvent(event: SeriesDetailsScreenEvents) {
        when (event) {

            is SeriesDetailsScreenEvents.Refresh -> {
                _seriesDetailsScreenState.update {
                    it.copy(
                        isLoading = true
                    )
                }

                startLoad(true)
            }

            is SeriesDetailsScreenEvents.SetDataAndLoad -> {
                _seriesDetailsScreenState.update {
                    it.copy(
                        moviesGenresList = event.moviesGenresList,
                        tvGenresList = event.tvGenresList,
                    )
                }

                startLoad(
                    isRefresh = false,
                    id = event.id,
                    type = event.type,
                    category = event.category
                )
            }
        }
    }

    private fun startLoad(
        isRefresh: Boolean,
        id: Int = seriesDetailsScreenState.value.series?.id ?: 0,
        type: String = seriesDetailsScreenState.value.series?.mediaType ?: "",
        category: String = seriesDetailsScreenState.value.series?.category ?: "",
    ) {

        loadMediaItem(
            id = id,
            type = type,
            category = category
        ) {
            loadDetails(
                isRefresh = isRefresh
            )

        }
    }

    private fun loadMediaItem(
        id: Int,
        type: String,
        category: String,
        onFinished: () -> Unit
    ) {
        viewModelScope.launch {
            _seriesDetailsScreenState.update {
                it.copy(
                    series = mediaRepository.getItem(
                        type = type,
                        category = category,
                        id = id,
                    )
                )
            }
            onFinished()
        }
    }

    private fun loadDetails(isRefresh: Boolean) {

        viewModelScope.launch {

            detailsRepository
                .getDetails(
                    id = seriesDetailsScreenState.value.series?.id ?: 0,
                    type = seriesDetailsScreenState.value.series?.mediaType ?: "",
                    isRefresh = isRefresh,
                    apiKey = API_KEY
                )
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { media ->
                                _seriesDetailsScreenState.update {
                                    it.copy(
                                        series = seriesDetailsScreenState.value.series?.copy(
                                            runtime = media.runtime,
                                            status = media.status,
                                            tagline = media.tagline,
                                        ),
                                        readableTime = MinutesToReadableTime(
                                            media.runtime ?: 0
                                        ).invoke()
                                    )
                                }
                            }
                        }

                        is Resource.Error -> Unit

                        is Resource.Loading -> {
                            _seriesDetailsScreenState.update {
                                it.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }
                    }
                }
        }
    }

}






