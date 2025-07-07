package com.example.travelguideapp.app_features.presentation.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelguideapp.R
import com.example.travelguideapp.app_features.presentation.adpater.TourismAdapter
import com.example.travelguideapp.app_features.presentation.viewModel.GetTourismViewModel
import com.example.travelguideapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var tourismAdapter: TourismAdapter
    private val tourismViewModel: GetTourismViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tourismAdapter = TourismAdapter()

        setUpTourismRecyclerView()
        observeTourismData()

        tourismViewModel.getTourismPlace("Category:Tourist_attractions_in_Myanmar")
        setUpOnFlagClickListener()
    }

    private fun setUpOnFlagClickListener() {
        binding.myanmarFlag.setOnClickListener { tourismViewModel.getTourismPlace("Category:Tourist_attractions_in_Myanmar") }
        binding.thailandFlag.setOnClickListener { tourismViewModel.getTourismPlace("Category:Tourist_attractions_in_Thailand") }
        binding.laosFlag.setOnClickListener { tourismViewModel.getTourismPlace("Category:Tourism_in_Laos") }
        binding.cambodiaFlag.setOnClickListener { tourismViewModel.getTourismPlace("Category:Tourism_in_Cambodia") }
        binding.malaysiaFlag.setOnClickListener { tourismViewModel.getTourismPlace("Category:Tourism_in_Malaysia") }
        binding.singaporeFlag.setOnClickListener { tourismViewModel.getTourismPlace("Category:Tourist_attractions_in_Singapore") }
        binding.indonesiaFlag.setOnClickListener { tourismViewModel.getTourismPlace("Category:Tourism_in_Indonesia") }
        binding.philippines.setOnClickListener { tourismViewModel.getTourismPlace("Category:Tourism_in_the_Philippines") }
        binding.vietnamFlag.setOnClickListener { tourismViewModel.getTourismPlace("Category:Tourism_in_Vietnam") }
        binding.bruneiFlag.setOnClickListener { tourismViewModel.getTourismPlace("Category:Tourism_in_Brunei") }
    }

    private fun setUpTourismRecyclerView() {
        binding.rcvPopularDestination.apply {
            adapter = tourismAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun observeTourismData() {
        lifecycleScope.launchWhenStarted {
            tourismViewModel.tourismPlaceState.collectLatest {  tourismState ->
                binding.loading.visibility = if(tourismState.isLoading == true) View.VISIBLE else View.GONE

                if (tourismState.error.isNotEmpty()) {
                    Toast.makeText(requireContext(), tourismState.error, Toast.LENGTH_SHORT).show()
                }

                tourismAdapter.differ.submitList(tourismState.tourismList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}