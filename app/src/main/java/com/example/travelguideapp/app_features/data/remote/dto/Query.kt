package com.example.travelguideapp.app_features.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("pages")
    val pages: Map<String, PlaceDto>?
)