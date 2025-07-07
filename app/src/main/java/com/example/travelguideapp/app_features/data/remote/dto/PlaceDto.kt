package com.example.travelguideapp.app_features.data.remote.dto


import com.example.travelguideapp.app_features.domain.model.PlaceVo
import com.google.gson.annotations.SerializedName

data class PlaceDto(
    @SerializedName("extract")
    val extract: String?,
    @SerializedName("ns")
    val ns: Int?,
    @SerializedName("pageid")
    val pageid: Int?,
    @SerializedName("pageimage")
    val pageimage: String?,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerializedName("title")
    val title: String?
){
    fun toPlaceVo(): PlaceVo {
        return PlaceVo(
            extract = extract ?: "Unknown",
            ns = ns ?: 0,
            pageid = pageid ?: 0,
            pageimage = pageimage ?: "Unknown",
            thumbnail = thumbnail?.source ?: "",
            title = title ?: "Unknown"
        )
    }
}