package com.example.travelguideapp.app_features.domain.useCase

import com.example.travelguideapp.app_core.Resource
import com.example.travelguideapp.app_features.domain.model.PlaceVo
import com.example.travelguideapp.app_features.domain.repository.TourismRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTourismPlacesUseCase @Inject constructor(
    private val repository: TourismRepository
) {
    operator fun invoke(tourismPlace: String): Flow<Resource<List<PlaceVo>>> = flow {
        emit(Resource.Loading())

        try {

            repository.getTourismPlaces(tourismPlace)
                .collect { place ->
                    emit(Resource.Success(place))
                }
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Oops! Something went wrong"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Please check your internet connection!"))
        }
    }
}