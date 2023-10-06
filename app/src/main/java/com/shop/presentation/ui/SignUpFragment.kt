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
import com.shop.databinding.FragmentSignupBinding
import com.shop.presentation.vm.AuthViewModel
import com.shop.utils.AuthResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            setViewModel()
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect { state ->
                        if (state.isLoading) {
                            binding.progressBarSignup.visibility = ProgressBar.VISIBLE
                        } else {
                            binding.progressBarSignup.visibility = ProgressBar.INVISIBLE
                        }
                    }
                }
            }
        }
        binding.logIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    private fun setViewModel() {

        val username = binding.etUsernameSignUp.text.toString()
        val password = binding.etPasswordSignUp.text.toString()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.signUp(username, password)
                viewModel.authResult.collect { result ->
                    when (result) {
                        is AuthResult.Authorized -> {
                                    findNavController().navigate(R.id.action_signUpFragment_to_containerActivity)
                        }

                        is AuthResult.Unauthorized -> {
                            Toast.makeText(context, "Sign up failed", Toast.LENGTH_LONG)
                                .show()
                        }

                        is AuthResult.Error -> {
                            Toast.makeText(context, "An unknown error occurred", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}

