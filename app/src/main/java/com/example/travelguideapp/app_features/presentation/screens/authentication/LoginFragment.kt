package com.example.travelguideapp.app_features.presentation.screens.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.travelguideapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setUpOnButtonClickListener()

    }

    private fun setUpOnButtonClickListener() {
        binding.txvRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }

        binding.btnLogin.setOnClickListener {
            handleLoginUser()
        }
    }

    private fun handleLoginUser() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        if (
            !checkValidOrInvalidUserInput(email, password)
        ) return

        CoroutineScope(Dispatchers.IO).launch {
            try {

                val result = auth.signInWithEmailAndPassword(email, password).await()
                val user = result.user

                withContext(Dispatchers.Main) {
                    if (user != null && user.isEmailVerified) {
                        Toast.makeText(
                            requireContext(),
                            "Login successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSelectCountryFragment())
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Please verify your email before logging in.",
                            Toast.LENGTH_LONG
                        ).show()
                        auth.signOut()
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }

    private fun checkValidOrInvalidUserInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.edtEmail.error = "Please enter your email"
            binding.edtEmail.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            binding.edtPassword.error = "Please enter a password"
            binding.edtPassword.requestFocus()
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}