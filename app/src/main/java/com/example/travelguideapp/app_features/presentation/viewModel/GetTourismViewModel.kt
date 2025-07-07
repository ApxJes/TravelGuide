package com.example.travelguideapp.app_features.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelguideapp.app_core.Resource
import com.example.travelguideapp.app_features.domain.useCase.GetTourismPlacesUseCase
import com.example.travelguideapp.app_features.presentation.state.TourismState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetTourismViewModel @Inject constructor(
   private val tourismPlacesUseCase: GetTourismPlacesUseCase
): ViewModel() {

    private var _tourismPlaceState: MutableStateFlow<TourismState> = MutableStateFlow(TourismState())
    val tourismPlaceState get() = _tourismPlaceState.asStateFlow()

    fun getTourismPlace(tourismPlace: String) {
        viewModelScope.launch {
            tourismPlacesUseCase(tourismPlace)
                .collect { resource ->
                    when(resource) {

                        is Resource.Loading -> {
                            _tourismPlaceState.value = TourismState(isLoading = true)
                        }

                        is Resource.Success -> {
                            _tourismPlaceState.value = TourismState(
                                tourismList = resource.data ?: emptyList(),
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            _tourismPlaceState.value = TourismState(
                                error = resource.message ?: "Unknown error",
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }
}