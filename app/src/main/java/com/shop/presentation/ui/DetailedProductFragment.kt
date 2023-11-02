package com.shop.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.shop.R
import com.shop.data.model.Accessory
import com.shop.data.model.Guitar
import com.shop.databinding.FragmentDetailedProductBinding
import com.shop.presentation.adapters.GenericRecyclerAdapter
import com.shop.presentation.vm.ProductViewModel
import com.shop.presentation.vm.RoomViewModel
import com.shop.utils.GenericClickListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailedProductFragment : Fragment() {

    private var _binding: FragmentDetailedProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()

    private val roomViewModel: RoomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val inputData = args?.getInt("id")

        val data = args?.getInt("number")

        getGuitarDetails(inputData)
        getAccessoryDetails(data)

        binding.detailsBackButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_two, SearchFragment()).commit()
            viewModelStore.clear()
        }
    }

    private fun getGuitarDetails(id: Int?) {
        val bindingInterface =
            object : GenericRecyclerAdapter.GenericRecyclerBindingInterface<Guitar> {
                override fun bindData(
                    item: Guitar,
                    view: View,
                    clickListener: GenericClickListener<Guitar>?,
                    position: Int
                ) {
                    val price = "$${item.price}"
                    val detailedGuitarImage: ImageView =
                        view.findViewById(R.id.detailed_guitar_image)
                    Picasso.get().load(item.image).rotate(90F).into(detailedGuitarImage)
                    val detailedGuitarName: TextView = view.findViewById(R.id.detailed_guitar_name)
                    detailedGuitarName.text = item.name
                    val detailedColorName: TextView = view.findViewById(R.id.detailed_name_color)
                    detailedColorName.text = item.color
                    val detailedPrice: TextView = view.findViewById(R.id.detailed_price)
                    detailedPrice.text = price
                    val description: TextView = view.findViewById(R.id.description)
                    description.text = item.description
                    val detailedColor: ImageView = view.findViewById(R.id.detailed_color)

                    if (item.color == "Black") {
                        detailedColor.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )
                    }
                    if(item.color == "Aubergine" ){
                        detailedColor.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.dark_red
                            )
                        )
                    }
                    if(item.color == "Gray"){
                        detailedColor.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.dark_grey
                            )
                        )
                    }
                    binding.detailsCartButton.setOnClickListener {
                        roomViewModel.addGuitarToCart(item)
                        binding.detailsCartButton.setImageResource(R.drawable.cart_check_icon)
                        Toast.makeText(context, "Added to cart", Toast.LENGTH_LONG).show()
                    }
                }
            }
        val adapter = GenericRecyclerAdapter(
            R.layout.item_guitar_details,
            bindingInterface
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (id != null) {
                    viewModel.loadGuitarById(id)
                }
                viewModel.guitarResult.collect {
                    adapter.submitList(it)
                }
            }
        }

        binding.detailsGuitarRv.adapter = adapter
        binding.detailsGuitarRv.layoutManager = GridLayoutManager(context, 1)
    }

    private fun getAccessoryDetails(id: Int?) {
        val bindingInterface =
            object : GenericRecyclerAdapter.GenericRecyclerBindingInterface<Accessory> {
                override fun bindData(
                    item: Accessory,
                    view: View,
                    clickListener: GenericClickListener<Accessory>?,
                    position: Int
                ) {
                    val price = "$${item.price}"
                    val detailedAccessoryImage: ImageView =
                        view.findViewById(R.id.detailed_accessory_image)
                    Picasso.get().load(item.image).into(detailedAccessoryImage)
                    val detailedAccessoryName: TextView = view.findViewById(R.id.detailed_accessory_name)
                    detailedAccessoryName.text = item.name
                    val detailedAccessoryPrice: TextView = view.findViewById(R.id.detailed_accessory_price)
                    detailedAccessoryPrice.text = price
                    val accessoryDescription: TextView = view.findViewById(R.id.accessory_description)
                    accessoryDescription.text = item.description

                    binding.detailsCartButton.setOnClickListener {
                        roomViewModel.addAccessoryToCart(item)
                        binding.detailsCartButton.setImageResource(R.drawable.cart_check_icon)
                        Toast.makeText(context, "Added to cart", Toast.LENGTH_LONG).show()
                    }
                }
            }
        val adapter = GenericRecyclerAdapter(
            R.layout.item_accessory_details,
            bindingInterface
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (id != null) {
                    viewModel.loadAccessoryById(id)
                }
                viewModel.accessoryResult.collect {
                    adapter.submitList(it)
                }
            }
        }
        binding.detailsAccessoryRv.adapter = adapter
        binding.detailsAccessoryRv.layoutManager = GridLayoutManager(context, 1)
    }
}