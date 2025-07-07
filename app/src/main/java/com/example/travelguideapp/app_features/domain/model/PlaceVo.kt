package com.example.travelguideapp.app_features.domain.model

import com.example.travelguideapp.app_features.data.remote.dto.PlaceDto
import com.example.travelguideapp.app_features.data.remote.dto.Thumbnail


data class PlaceVo(
    val extract: String,
    val ns: Int,
    val pageid: Int,
    val pageimage: String,
    val thumbnail: String,
    val title: String
){
    fun toPlaceDto(): PlaceDto {
        return PlaceDto(
            extract = extract,
            ns = ns,
            pageid = pageid,
            pageimage = pageimage,
            thumbnail = Thumbnail(source = thumbnail),
            title = title
        )
    }
}
