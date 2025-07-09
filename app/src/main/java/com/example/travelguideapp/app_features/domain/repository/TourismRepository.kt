package com.example.travelguideapp.app_features.domain.repository

import com.example.travelguideapp.app_core.Resource
import com.example.travelguideapp.app_features.domain.model.PlaceVo
import kotlinx.coroutines.flow.Flow

interface TourismRepository {

    suspend fun getTourismPlaces(tourismPlace: String): Flow<List<PlaceVo>>

    suspend fun getCountryCuisine(countryCuisine: String): Flow<List<PlaceVo>>

    suspend fun getDetails(id: String): Flow<PlaceVo>
}