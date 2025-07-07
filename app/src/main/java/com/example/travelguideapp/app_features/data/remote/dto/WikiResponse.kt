package com.example.travelguideapp.app_features.data.remote.dto


import com.google.gson.annotations.SerializedName

data class WikiResponse(
    @SerializedName("batchcomplete")
    val batchcomplete: String?,
    @SerializedName("query")
    val query: Query?
)