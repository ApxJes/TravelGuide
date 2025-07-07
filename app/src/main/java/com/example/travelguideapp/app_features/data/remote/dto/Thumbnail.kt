package com.example.travelguideapp.app_features.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("source")
    val source: String?,
    @SerializedName("width")
    val width: Int? = null
)