package com.example.colorphone.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.colorphone.databinding.FragmentLoginBinding
import com.example.colorphone.ui.core.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import androidx.core.os.bundleOf
import com.example.colorphone.R

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun setUpListener() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    when (state) {
                        is LoginViewModel.LoginState.Loading -> {
                            binding.btnLogin.isEnabled = false
                        }
                        is LoginViewModel.LoginState.Success -> {
                            binding.btnLogin.isEnabled = true
                            Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
                            val showtimeId = arguments?.getLong("showtimeId", -1L) ?: -1L
                            if (showtimeId != -1L) {
                                findNavController().navigate(
                                    R.id.action_loginFragment_to_bookingFragment,
                                    bundleOf("showtimeId" to showtimeId)
                                )
                            } else {
                                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                            }
                        }
                        is LoginViewModel.LoginState.Error -> {
                            binding.btnLogin.isEnabled = true
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            binding.btnLogin.isEnabled = true
                        }
                    }
                }
            }
        }
    }
}
