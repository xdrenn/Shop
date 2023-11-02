package com.shop.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shop.MainActivity
import com.shop.R
import com.shop.databinding.FragmentProfileBinding
import com.shop.presentation.vm.AuthViewModel
import com.shop.utils.DeleteResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUserRv()

        binding.signOutTv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_two, SignUpFragment()).commit()
        }
    }

    private fun initUserRv() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getUser()
                viewModel.userResult.collect {
                    binding.profileName.text = it.username
                    val id = it.id
                    binding.deleteTv.setOnClickListener {
                        viewModel.delete(id)
                    }
                    viewModel.deleteResult.collect { result ->
                        when (result) {
                            is DeleteResult.Success -> {
                                Toast.makeText(context, "Account has been deleted", Toast.LENGTH_LONG)
                                    .show()
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.nav_host_fragment_two, SignUpFragment())
                                    .commit()
                            }

                            is DeleteResult.Failure -> {
                                Toast.makeText(context, "Delete failed", Toast.LENGTH_LONG)
                                    .show()
                            }

                            is DeleteResult.Error -> {
                                Toast.makeText(
                                    context,
                                    "An unknown error occurred",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }
}