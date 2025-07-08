package com.example.travelguideapp.app_features.presentation.state

import com.example.travelguideapp.app_features.domain.model.PlaceVo

data class CuisineState(
    val isLoading: Boolean = false,
    val cuisineList: List<PlaceVo> = emptyList(),
    val error: String = ""
)