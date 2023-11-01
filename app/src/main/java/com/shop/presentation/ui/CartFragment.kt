package com.shop.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.shop.R
import com.shop.data.model.Accessory
import com.shop.data.model.Guitar
import com.shop.databinding.FragmentCartBinding
import com.shop.presentation.adapters.GenericRecyclerAdapter
import com.shop.presentation.vm.RoomViewModel
import com.shop.utils.GenericClickListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RoomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGuitarsCart()
        initAccessoriesCart()
    }

    private fun initGuitarsCart() {
        val bindingInterface =
            object : GenericRecyclerAdapter.GenericRecyclerBindingInterface<Guitar> {
                override fun bindData(
                    item: Guitar,
                    view: View,
                    clickListener: GenericClickListener<Guitar>?,
                    position: Int
                ) {
                    val imageView: ImageView = view.findViewById(R.id.cart_img)
                    Picasso.get().load(item.image).into(imageView)
                    val textView: TextView = view.findViewById(R.id.cart_name)
                    textView.text = item.name
                    val secondTextView: TextView = view.findViewById(R.id.cart_price)
                    secondTextView.text = item.price.toString()
                    val deleteBtn: ImageView = view.findViewById(R.id.delete_button)

                    viewModel.addGuitarToCart(item)

                    deleteBtn.setOnClickListener {
                        viewLifecycleOwner.lifecycleScope.launch {
                            repeatOnLifecycle(Lifecycle.State.STARTED) {
                                withContext(Dispatchers.IO) {
                                    val guitar = viewModel.getGuitarCartById(item.id)
                                    if (guitar != null) {
                                        viewModel.deleteGuitarCart(guitar)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        val adapter = GenericRecyclerAdapter(
            R.layout.item_cart,
            bindingInterface
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.getGuitarCarts().collect {
                    adapter.submitList(it)
                }
            }
        }

        binding.cartGuitarsRv.adapter = adapter
        binding.cartGuitarsRv.layoutManager = GridLayoutManager(context, 1)
    }

    private fun initAccessoriesCart() {
        val bindingInterface =
            object : GenericRecyclerAdapter.GenericRecyclerBindingInterface<Accessory> {
                override fun bindData(
                    item: Accessory,
                    view: View,
                    clickListener: GenericClickListener<Accessory>?,
                    position: Int
                ) {
                    val imageView: ImageView = view.findViewById(R.id.cart_img)
                    Picasso.get().load(item.image).into(imageView)
                    val textView: TextView = view.findViewById(R.id.cart_name)
                    textView.text = item.name
                    val secondTextView: TextView = view.findViewById(R.id.cart_price)
                    secondTextView.text = item.price.toString()
                    val deleteBtn: ImageView = view.findViewById(R.id.delete_button)

                    viewModel.addAccessoryToCart(item)

                    deleteBtn.setOnClickListener {
                        viewLifecycleOwner.lifecycleScope.launch {
                            repeatOnLifecycle(Lifecycle.State.STARTED) {
                                withContext(Dispatchers.IO) {
                                    val accessory = viewModel.getAccessoryCartById(item.id)
                                    if (accessory != null) {
                                        viewModel.deleteAccessoryCart(accessory)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        val adapter = GenericRecyclerAdapter(
            R.layout.item_cart,
            bindingInterface
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAccessoriesCart().collect {
                    adapter.submitList(it)
                }
            }
        }

        binding.cartAccessoriesRv.adapter = adapter
        binding.cartAccessoriesRv.layoutManager = GridLayoutManager(context, 1)
    }
}