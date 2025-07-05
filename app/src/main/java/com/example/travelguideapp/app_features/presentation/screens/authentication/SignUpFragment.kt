package com.example.travelguideapp.app_features.presentation.screens.authentication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.travelguideapp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setUpOnButtonClickListener()
    }

    private fun setUpOnButtonClickListener() {
        binding.txvSignIn.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }

        binding.btnRegister.setOnClickListener {
            handleUserRegistration()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun handleUserRegistration() {
        val userEmail = binding.edtEmail.text.toString()
        val userPhoneNumber = binding.edtPhone.text.toString()
        val firstName = binding.edtFirstName.text.toString()
        val lastName = binding.edtLastName.text.toString()
        val password = binding.edtPassword.text.toString()
        val confirmPassword = binding.edtConfirmPassword.text.toString()

        if (
            !checkValidOrInvalidUserInput (
                userEmail,
                userPhoneNumber,
                firstName, lastName,
                password,
                confirmPassword
            )
        ) return

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = auth.createUserWithEmailAndPassword(userEmail, password).await()
                    val user = result.user
                    user?.sendEmailVerification()?.await()

                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Successfully signed up. Please check your email for verification.",
                            Toast.LENGTH_SHORT
                        ).show()

                        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            e.message, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    private fun checkValidOrInvalidUserInput(
        email: String,
        phoneNumber: String,
        firstName: String,
        lastName: String,
        password: String,
        confirmPassword: String,
    ): Boolean {

        if (email.isEmpty()) {
            binding.edtEmail.error = "Please enter your email"
            binding.edtEmail.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.error = "Please enter a valid email address"
            binding.edtEmail.requestFocus()
            return false
        }

        if (firstName.isEmpty()) {
            binding.edtFirstName.error = "Please enter your first name"
            binding.edtFirstName.requestFocus()
            return false
        }

        if (lastName.isEmpty()) {
            binding.edtLastName.error = "Please enter your last name"
            binding.edtLastName.requestFocus()
            return false
        }

        if (phoneNumber.isEmpty()) {
            binding.edtPhone.error = "Please enter your phone number"
            binding.edtPhone.requestFocus()
            return false
        }

        val phoneRegex = Regex("^[0-9]{10,15}$")
        if (!phoneRegex.matches(phoneNumber)) {
            binding.edtPhone.error = "Please enter a valid phone number"
            binding.edtPhone.requestFocus()
            return false
        }


        if (password.isEmpty()) {
            binding.edtPassword.error = "Please enter a password"
            binding.edtPassword.requestFocus()
            return false
        }

        if (!password.any { it.isUpperCase() }) {
            binding.edtPassword.error = "Password must contain at least one uppercase letter"
            binding.edtPassword.requestFocus()
            return false
        }

        if (confirmPassword.isEmpty()) {
            binding.edtConfirmPassword.error = "Please confirm your password"
            binding.edtConfirmPassword.requestFocus()
            return false
        }

        if (confirmPassword != password) {
            binding.edtConfirmPassword.error = "Passwords do not match"
            binding.edtConfirmPassword.requestFocus()
            return false
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}