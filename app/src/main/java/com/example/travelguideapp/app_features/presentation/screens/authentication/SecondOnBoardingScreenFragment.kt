package com.example.travelguideapp.app_features.presentation.screens.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.travelguideapp.R
import com.example.travelguideapp.databinding.FragmentSecondOnBoardingScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondOnBoardingScreenFragment : Fragment() {
    private var _binding: FragmentSecondOnBoardingScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondOnBoardingScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}