package com.app.series.search.presentation

import com.app.series.main.domain.models.Series

sealed class SearchUiEvents {
    data class Refresh(val type: String) : SearchUiEvents()
    data class OnPaginate(val type: String) : SearchUiEvents()
    data class OnSearchQueryChanged(val query: String) : SearchUiEvents()
    data class OnSearchedItemClick(val series: Series) : SearchUiEvents()
}