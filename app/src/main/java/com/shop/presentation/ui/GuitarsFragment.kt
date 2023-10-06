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
import com.shop.databinding.FragmentGuitarsBinding
import com.shop.presentation.adapters.GenericRecyclerAdapter
import com.shop.presentation.adapters.TwoGenericsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuitarsFragment : Fragment() {


    private var _binding: FragmentGuitarsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuitarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMusiciansRecyclerView()
        initNewGuitarsRecyclerView()

    }

    private fun initMusiciansRecyclerView() {
        val bindingInterface = object : GenericRecyclerAdapter.GenericRecyclerBindingInterface<Int>{
            override fun bindData(item : Int, view: View){
                val imageView: ImageView = view.findViewById(R.id.layout_img)
                imageView.setImageResource(item)
            }
        }
        binding.musiciansRv.adapter = GenericRecyclerAdapter(
            listOf(
               R.drawable.jimi_hendrix,
                R.drawable.bob_marley,
                R.drawable.john_lennon
            ),
            R.layout.item_layout,
            bindingInterface
        )
        binding.musiciansRv.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
    }

    private fun initNewGuitarsRecyclerView() {
        val bindingInterface =
            object : TwoGenericsRecyclerAdapter.TwoGenericsRecyclerBindingInterface<String, Int> {
                override fun bindData(
                    item: String,
                    secondItem: Int,
                    view: View,
                    clickListener: TwoGenericsRecyclerAdapter.GenericClickListener<String>?,
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
                "Vintera II '60s Jazz Bass",
                "Aerodyne Special Telecaster",
                "Vintera II '70s Telecaster Deluxe with Tremolo",
                "Les Paul Standard 50s Plain Top Inverness Green",
                "Les Paul Special Deep Purple",
                "Japan Limited Hybrid II Telecaster, Noir"
            ),
            listOf(
                R.drawable.vintera_jazz_bass_60s,
                R.drawable.aerodyne_special_telecaster,
                R.drawable.vintera_telecaster_70s,
                R.drawable.les_paul_standart_50s_plain_top,
                R.drawable.les_paul_deep_purple,
                R.drawable.fender_telecaster_noir,

                ),
            R.layout.item_new,
            bindingInterface
        )
        binding.newGuitarsRv.adapter = adapter
        binding.newGuitarsRv.layoutManager = GridLayoutManager(context, 2)
    }
}