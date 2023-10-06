package com.shop.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.shop.R
import com.shop.databinding.FragmentLoginBinding
import com.shop.presentation.vm.AuthViewModel
import com.shop.utils.AuthResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            setViewModel()
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect { state ->
                        if (state.isLoading) {
                            binding.progressBarLogin.visibility = ProgressBar.VISIBLE
                        } else {
                            binding.progressBarLogin.visibility = ProgressBar.INVISIBLE
                        }
                    }
                }
            }
        }

        binding.registerHere.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun setViewModel() {
        val login = binding.etLogin.text.toString()
        val password = binding.etPassword.text.toString()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.login(login, password)
                viewModel.authResult.collect { result ->
                    when (result) {
                        is AuthResult.Authorized -> {
                            findNavController().navigate(R.id.action_loginFragment_to_containerActivity)
                        }

                        is AuthResult.Unauthorized -> {
                            Toast.makeText(
                                context,
                                "You're not authorized",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is AuthResult.Error -> {
                            Toast.makeText(
                                context,
                                "An unknown error occurred",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}