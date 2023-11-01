package com.shop.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shop.R
import com.shop.databinding.FragmentAccessoriesBinding
import com.shop.presentation.adapters.GenericRecyclerAdapter
import com.shop.presentation.adapters.TwoGenericsRecyclerAdapter
import com.shop.utils.GenericClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccessoriesFragment : Fragment() {

    private var _binding: FragmentAccessoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccessoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAccessoriesRecyclerView()
        initNewAccessoriesRecyclerView()
    }

    private fun initAccessoriesRecyclerView() {
        val bindingInterface =
            object : GenericRecyclerAdapter.GenericRecyclerBindingInterface<Int> {
                override fun bindData(
                    item: Int,
                    view: View,
                    clickListener: GenericClickListener<Int>?,
                    position: Int
                ) {
                    val imageView: ImageView = view.findViewById(R.id.layout_img)
                    imageView.setImageResource(item)
                }
            }
        val adapter = GenericRecyclerAdapter(
            R.layout.item_layout,
            bindingInterface
        )
        adapter.submitList(
            listOf(
                R.drawable.jimi_hendrix,
                R.drawable.bob_marley,
                R.drawable.john_lennon
            )
        )
        binding.accessoriesRv.adapter = adapter
        binding.accessoriesRv.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
    }

    private fun initNewAccessoriesRecyclerView() {
        val bindingInterface =
            object : TwoGenericsRecyclerAdapter.TwoGenericsRecyclerBindingInterface<String, Int> {
                override fun bindData(
                    item: String,
                    secondItem: Int,
                    view: View,
                    clickListener: GenericClickListener<String>?,
                    position: Int
                ) {
                    val textView: TextView = view.findViewById(R.id.new_name)
                    val imageView: ImageView = view.findViewById(R.id.new_image)
                    textView.text = item
                    imageView.setImageResource(secondItem)
                }
            }

        val adapter = TwoGenericsRecyclerAdapter(
            listOf(
                "Tom DeLonge 351 Celluloid Picks ",
                "George Harrison All The Things Must Pass Strap",
                "Classic Series Case Stand",
                "Fender x Wrangler Denim Straps",
                "Tone Master Pro",
                "Professional Series Tweed Instrument Cables",


                ),
            listOf(
                R.drawable.tom_delonge_picks,
                R.drawable.strap_harrison,
                R.drawable.guitars_stand,
                R.drawable.wrangler_straps,
                R.drawable.tone_master_pro,
                R.drawable.tweed_cables,

                ),
            R.layout.item_new,
            bindingInterface
        )
        binding.newAccessoriesRv.adapter = adapter
        binding.newAccessoriesRv.layoutManager = GridLayoutManager(context, 2)
    }
}