package com.app.series.main.presentation.main

sealed class MainUiEvents {
    data class Refresh(val type: String) : MainUiEvents()
    data class OnPaginate(val type: String) : MainUiEvents()
}