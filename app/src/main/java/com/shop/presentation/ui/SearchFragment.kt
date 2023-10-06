package com.shop.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shop.R
import com.shop.databinding.FragmentSearchBinding
import com.shop.presentation.adapters.TwoGenericsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCategoryRecyclerView()
    }

    private fun initCategoryRecyclerView() {
        val recyclerView = binding.rvCategory
        val bindingInterface =
            object : TwoGenericsRecyclerAdapter.TwoGenericsRecyclerBindingInterface<Int, String> {
                override fun bindData(
                    item: Int,
                    secondItem: String,
                    view: View,
                    clickListener: TwoGenericsRecyclerAdapter.GenericClickListener<Int>?,
                    position: Int
                ) {
                    val textView: TextView = view.findViewById(R.id.tv_category_name)
                    val imageView: ImageView = view.findViewById(R.id.category_img)
                    imageView.setImageResource(item)
                    textView.text = secondItem
                }
            }
        val adapter = TwoGenericsRecyclerAdapter(
            listOf(
                R.drawable.electric_guitar,
                R.drawable.acoustic_guitar,
                R.drawable.bass_guitar,
                R.drawable.combo_amp
            ), listOf(
                "Electric",
                "Acoustic",
                "Bass",
                "Accessories"
            ),
            R.layout.item_category,
            bindingInterface
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        adapter.setOnClickListener(object : TwoGenericsRecyclerAdapter.GenericClickListener<Int> {
            override fun onClick(position: Int, data: Int) {
                if (position == 1) {
                    findNavController().navigate(R.id.action_searchFragment_to_productsFragment)
                }
                if (position == 2) {
                    findNavController().navigate(R.id.action_searchFragment_to_productsFragment)
                }
                if (position == 3) {
                    findNavController().navigate(R.id.action_searchFragment_to_productsFragment)
                }
            }
        })

    }
}