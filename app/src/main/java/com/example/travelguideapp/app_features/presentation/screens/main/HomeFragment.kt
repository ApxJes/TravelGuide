package com.example.travelguideapp.app_features.presentation.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelguideapp.app_features.domain.model.Country
import com.example.travelguideapp.app_features.presentation.adpater.CountryAdapter
import com.example.travelguideapp.app_features.presentation.adpater.CuisineAdapter
import com.example.travelguideapp.app_features.presentation.adpater.TourismAdapter
import com.example.travelguideapp.app_features.presentation.viewModel.GetCuisineViewModel
import com.example.travelguideapp.app_features.presentation.viewModel.GetTourismViewModel
import com.example.travelguideapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var tourismAdapter: TourismAdapter
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var cuisineAdapter: CuisineAdapter

    private val tourismViewModel: GetTourismViewModel by viewModels()
    private val cuisineViewModel: GetCuisineViewModel by viewModels()

    private val cuisineCategoryMap = mapOf(
        "Myanmar" to "Burmese_cuisine",
        "Thailand" to "Thai_cuisine",
        "Vietnam" to "Vietnamese_cuisine",
        "Indonesia" to "Indonesian_cuisine",
        "Philippines" to "Philippine_desserts",
        "Malaysia" to "Malaysian_cuisine",
        "Cambodia" to "Cambodian_cuisine",
        "Laos" to "Lao_cuisine",
        "Singapore" to "Singaporean_cuisine",
        "Brunei" to "Bruneian_cuisine"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()
        setupRecyclerViews()
        setupObservers()
        setupFlagClickListeners()

        tourismViewModel.getTourismPlace("Category:Tourist_attractions_in_Myanmar")
        cuisineViewModel.getCountryCuisine("Category:Burmese_cuisine")
    }

    @SuppressLint("SetTextI18n")
    private fun initAdapters() {
        tourismAdapter = TourismAdapter()
        cuisineAdapter = CuisineAdapter()

        val countries = listOf(
            Country("Myanmar"),
            Country("Thailand"),
            Country("Vietnam"),
            Country("Indonesia"),
            Country("Philippines"),
            Country("Malaysia"),
            Country("Cambodia"),
            Country("Laos"),
            Country("Singapore"),
            Country("Brunei")
        )

        countryAdapter = CountryAdapter(countries) { selectedCountry ->
            binding.txvPopularFoodInCountry.text = "Popular Cuisine in ${selectedCountry.name}"

            val cuisineCategory = cuisineCategoryMap[selectedCountry.name]
            if (cuisineCategory != null) {
                cuisineViewModel.getCountryCuisine("Category:$cuisineCategory")
            } else {
                Toast.makeText(context, "No cuisine category found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.rcvPopularDestination.apply {
            adapter = tourismAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rcvCountryList.apply {
            adapter = countryAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rcvCuisine.apply {
            adapter = cuisineAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        tourismAdapter.setOnClickListener {
            Log.d("HomeFragment", "Clicked: ${it.pageid}")
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it.pageid.toString())
            findNavController().navigate(action)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            tourismViewModel.tourismPlaceState.collectLatest { state ->
                binding.loading.visibility = if (state.isLoading == true) View.VISIBLE else View.GONE

                if (state.error.isNotEmpty()) {
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                }

                tourismAdapter.differ.submitList(state.tourismList)
            }
        }

        lifecycleScope.launchWhenStarted {
            cuisineViewModel.getCountryCuisineState.collectLatest { state ->
                binding.loading.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                if (state.error.isNotEmpty()) {
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                }

                cuisineAdapter.differ.submitList(state.cuisineList)
            }
        }
    }

    private fun setupFlagClickListeners() {
        binding.myanmarFlag.setOnClickListener {
            tourismViewModel.getTourismPlace("Category:Tourist_attractions_in_Myanmar")
        }
        binding.thailandFlag.setOnClickListener {
            tourismViewModel.getTourismPlace("Category:Tourist_attractions_in_Thailand")
        }
        binding.laosFlag.setOnClickListener {
            tourismViewModel.getTourismPlace("Category:Tourism_in_Laos")
        }
        binding.cambodiaFlag.setOnClickListener {
            tourismViewModel.getTourismPlace("Category:Tourism_in_Cambodia")
        }
        binding.malaysiaFlag.setOnClickListener {
            tourismViewModel.getTourismPlace("Category:Tourism_in_Malaysia")
        }
        binding.singaporeFlag.setOnClickListener {
            tourismViewModel.getTourismPlace("Category:Tourist_attractions_in_Singapore")
        }
        binding.indonesiaFlag.setOnClickListener {
            tourismViewModel.getTourismPlace("Category:Tourism_in_Indonesia")
        }
        binding.philippines.setOnClickListener {
            tourismViewModel.getTourismPlace("Category:Tourism_in_the_Philippines")
        }
        binding.vietnamFlag.setOnClickListener {
            tourismViewModel.getTourismPlace("Category:Tourism_in_Vietnam")
        }
        binding.bruneiFlag.setOnClickListener {
            tourismViewModel.getTourismPlace("Category:Tourism_in_Brunei")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
