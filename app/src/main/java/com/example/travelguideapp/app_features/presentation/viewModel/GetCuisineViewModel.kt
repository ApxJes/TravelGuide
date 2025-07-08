package com.example.travelguideapp.app_features.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelguideapp.app_core.Resource
import com.example.travelguideapp.app_features.domain.useCase.GetCountryCuisinesUseCase
import com.example.travelguideapp.app_features.presentation.state.CuisineState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetCuisineViewModel @Inject constructor(
    private val cuisinesUseCase: GetCountryCuisinesUseCase
) : ViewModel() {

    private var _getCountryCuisineState: MutableStateFlow<CuisineState> =
        MutableStateFlow(CuisineState())
    val getCountryCuisineState = _getCountryCuisineState.asStateFlow()

    fun getCountryCuisine(cuisine: String) {
        viewModelScope.launch {
            cuisinesUseCase(cuisine)
                .collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _getCountryCuisineState.value = CuisineState(
                                isLoading = false,
                                error = resource.message ?: "Unknown error"
                            )
                        }

                        is Resource.Loading -> {
                            _getCountryCuisineState.value = CuisineState(
                                isLoading = true
                            )
                        }
                        is Resource.Success-> {
                            _getCountryCuisineState.value = CuisineState(
                                isLoading = false,
                                cuisineList =  resource.data ?:  emptyList()
                            )
                        }
                    }
                }
        }
    }
}