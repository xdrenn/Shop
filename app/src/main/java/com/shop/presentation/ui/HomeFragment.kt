package com.shop.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.shop.R
import com.shop.databinding.FragmentHomeBinding
import com.shop.presentation.adapters.GenericRecyclerAdapter
import com.shop.presentation.adapters.VpAdapter
import com.shop.utils.GenericClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBannerRecyclerView()


        binding.vp.adapter = VpAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.vp) { tab, position ->
            if (position == 0) {
                tab.text = "Guitars"
            }
            if (position == 1) {
                tab.text = "Accessories"
            }
        }.attach()
    }

    private fun initBannerRecyclerView() {
        val bindingInterface =
            object : GenericRecyclerAdapter.GenericRecyclerBindingInterface<Int> {
                override fun bindData(
                    item: Int,
                    view: View,
                    clickListener: GenericClickListener<Int>?,
                    position: Int
                ) {
                    val imageView: ImageView = view.findViewById(R.id.banner_image)
                    imageView.setImageResource(item)
                }
            }
        val adapter = GenericRecyclerAdapter(
            R.layout.item_banner,
            bindingInterface
        )
        adapter.submitList(
            listOf(
                R.drawable.banner_one,
                R.drawable.banner_four,
                R.drawable.banner_three
            )
        )
        binding.rvBanner.adapter = adapter
        binding.rvBanner.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
    }
}