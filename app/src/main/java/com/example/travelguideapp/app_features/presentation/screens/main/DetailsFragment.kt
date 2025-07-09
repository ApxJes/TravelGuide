package com.example.travelguideapp.app_features.presentation.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.travelguideapp.R
import com.example.travelguideapp.app_features.presentation.viewModel.GetDetailsViewModel
import com.example.travelguideapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: GetDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagesId = args.pagesId
        viewModel.getDetails(pagesId)
        observeDetails()

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun observeDetails() {
        lifecycleScope.launchWhenStarted {
            viewModel.detailsState.collectLatest { state ->
                binding.loading.visibility = if(state.isLoading == true) View.VISIBLE else View.GONE

                if(state.details != null) {
                    binding.itemName.text = state.details.title
                    binding.itemDescription.text = state.details.extract
                    Glide.with(this@DetailsFragment).load(state.details.thumbnail).into(binding.imgPlace)
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}