package com.example.travelguideapp.app_features.data.apiService

import com.example.travelguideapp.app_features.data.remote.dto.PlaceDto
import com.example.travelguideapp.app_features.data.remote.dto.WikiResponse
import com.example.travelguideapp.app_features.domain.model.PlaceVo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WikipediaApiService {

    @GET("w/api.php")
    suspend fun getTouristPlaces(
        @Query("action") action: String = "query",
        @Query("generator") generator: String = "categorymembers",
        @Query("gcmtitle") categoryTitle: String,
        @Query("gcmlimit") limit: Int = 10,
        @Query("gcmtype") type: String = "page",
        @Query("prop") prop: String = "pageimages|extracts",
        @Query("exintro") exintro: Boolean = true,
        @Query("explaintext") explainText: Boolean = true,
        @Query("pithumbsize") thumbSize: Int = 500,
        @Query("format") format: String = "json"
    ): Response<WikiResponse>

    @GET("w/api.php")
    suspend fun getCountryCuisine(
        @Query("action") action: String = "query",
        @Query("generator") generator: String = "categorymembers",
        @Query("gcmtitle") gcmtitle: String,
        @Query("gcmlimit") gcmlimit: Int = 10,
        @Query("gcmtype") gcmtype: String = "page",
        @Query("prop") prop: String = "pageimages|extracts",
        @Query("exintro") exintro: Boolean = true,
        @Query("explaintext") explaintext: Boolean = true,
        @Query("pithumbsize") pithumbsize: Int = 500,
        @Query("format") format: String = "json"
    ): Response<WikiResponse>
}