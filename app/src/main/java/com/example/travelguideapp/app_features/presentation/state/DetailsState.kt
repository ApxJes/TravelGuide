package com.example.travelguideapp.app_features.presentation.state

import com.example.travelguideapp.app_features.domain.model.PlaceVo

data class DetailsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val details: PlaceVo? = null
)