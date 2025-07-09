package com.example.travelguideapp.app_features.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelguideapp.app_core.Resource
import com.example.travelguideapp.app_features.domain.useCase.GetTourismDetailsUseCase
import com.example.travelguideapp.app_features.presentation.state.DetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetDetailsViewModel @Inject constructor(
    private val tourismDetailsUseCase: GetTourismDetailsUseCase
): ViewModel(){

    private var _detailsState: MutableStateFlow<DetailsState> = MutableStateFlow(DetailsState())
    val detailsState: StateFlow<DetailsState> = _detailsState.asStateFlow()

    fun getDetails(id: String) {
        viewModelScope.launch {
            tourismDetailsUseCase(id)
                .collect { resource ->
                    when(resource) {
                        is Resource.Loading -> {
                            _detailsState.value = DetailsState(
                                isLoading = true
                            )
                        }

                        is  Resource.Success -> {
                            _detailsState.value = DetailsState(
                                isLoading = false,
                                details = resource.data
                            )
                        }

                        is Resource.Error -> {
                            _detailsState.value = DetailsState(
                                isLoading = false,
                                error = resource.message ?: "Unknown error"
                            )
                        }
                    }
                }
        }
    }
}