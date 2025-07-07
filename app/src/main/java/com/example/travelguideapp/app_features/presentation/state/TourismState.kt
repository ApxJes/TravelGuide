package com.example.travelguideapp.app_features.presentation.state

import com.example.travelguideapp.app_features.domain.model.PlaceVo

data class TourismState(
    val isLoading: Boolean = false,
    val tourismList: List<PlaceVo> = emptyList(),
    val error: String = ""
)