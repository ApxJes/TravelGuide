package com.example.travelguideapp.app_features.data.repository

import com.example.travelguideapp.app_features.data.apiService.WikipediaApiService
import com.example.travelguideapp.app_features.domain.model.PlaceVo
import com.example.travelguideapp.app_features.domain.repository.TourismRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TourismRepositoryImpl @Inject constructor(
    private val api: WikipediaApiService
): TourismRepository {
    override suspend fun getTourismPlaces(tourismPlace: String): Flow<List<PlaceVo>> = flow {

        try {
            val response = api.getTouristPlaces(categoryTitle = tourismPlace)
            if(response.isSuccessful) {
                val body = response.body()
                val pagesMap = body?.query?.pages
                val places = pagesMap?.values?.map { it.toPlaceVo() } ?: emptyList()
                emit(places)
            }
        } catch (e: Exception) {
            throw  e
        }
    }

    override suspend fun getCountryCuisine(countryCuisine: String): Flow<List<PlaceVo>> = flow {

        try {
            val response = api.getCountryCuisine(gcmtitle = countryCuisine)
            if(response.isSuccessful) {
                val body = response.body()
                val pagesMap = body?.query?.pages
                val cuisines = pagesMap?.values?.map { it.toPlaceVo() } ?:  emptyList()
                emit(cuisines)
            }
        } catch (e: Exception) {
            throw  e
        }
    }

    override suspend fun getDetails(id: String): Flow<PlaceVo> = flow {
        try {
            val response = api.getPageDetails(pageIds = id)
            if (response.isSuccessful) {
                val body = response.body()
                val place = body?.query?.pages?.values?.firstOrNull()?.toPlaceVo()
                place?.let { emit(place) }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}