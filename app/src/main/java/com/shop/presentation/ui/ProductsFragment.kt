package com.shop.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.shop.R
import com.shop.data.model.Accessory
import com.shop.data.model.Guitar
import com.shop.databinding.FragmentProductBinding
import com.shop.presentation.adapters.GenericRecyclerAdapter
import com.shop.presentation.vm.ProductViewModel
import com.shop.utils.GenericClickListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()

    private var bundle = Bundle()

    private val fragment = DetailedProductFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val inputData = args?.getString("data")
        initGuitarsRv(inputData)

        binding.backButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_two, SearchFragment()).commit()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.isLoading) {
                        binding.progressBarProducts.visibility = ProgressBar.VISIBLE
                    } else {
                        binding.progressBarProducts.visibility = ProgressBar.INVISIBLE
                    }
                    if (state.isError) {
                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun initGuitarsRv(string: String?) {
        val bindingInterface =
            object : GenericRecyclerAdapter.GenericRecyclerBindingInterface<Guitar> {
                override fun bindData(
                    item: Guitar,
                    view: View,
                    clickListener: GenericClickListener<Guitar>?,
                    position: Int
                ) {
                    val price = "$${item.price}"
                    val imageView: ImageView = view.findViewById(R.id.product_image)
                    Picasso.get().load(item.image).into(imageView)
                    val textView: TextView = view.findViewById(R.id.product_name)
                    textView.text = item.name
                    val secondTextView: TextView = view.findViewById(R.id.product_price)
                    secondTextView.text = price
                }
            }
        val adapter = GenericRecyclerAdapter(
            R.layout.item_product,
            bindingInterface
        )

        val secondBindingInterface =
            object : GenericRecyclerAdapter.GenericRecyclerBindingInterface<Accessory> {
                override fun bindData(
                    item: Accessory,
                    view: View,
                    clickListener: GenericClickListener<Accessory>?,
                    position: Int
                ) {
                    val price = "$${item.price}"
                    val imageView: ImageView = view.findViewById(R.id.product_image)
                    Picasso.get().load(item.image).into(imageView)
                    val textView: TextView = view.findViewById(R.id.product_name)
                    textView.text = item.name
                    val secondTextView: TextView = view.findViewById(R.id.product_price)
                    secondTextView.text = price
                }
            }
        val secondAdapter = GenericRecyclerAdapter(
            R.layout.item_product,
            secondBindingInterface
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadGuitarByCategory(string!!)
                viewModel.loadGuitarByBrand(string)
                viewModel.loadAccessoryBySubcategory(string)
                viewModel.loadAccessoryByCategory(string)
                viewModel.guitarResult.collect {
                    if (it.isNotEmpty()) {
                        adapter.submitList(it)
                    } else {
                        viewModel.accessoryResult.collect { list ->
                            secondAdapter.submitList(list)
                        }
                    }
                }
            }
        }

        binding.svProducts.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onQueryTextSubmit(text: String?): Boolean {
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            if (text != null) {
                                adapter.submitList(null)
                                secondAdapter.submitList(null)
                                viewModel.loadGuitarByCategory(text)
                                viewModel.loadGuitarByBrand(text)
                                viewModel.loadAccessoryBySubcategory(text)
                                viewModel.loadAccessoryByCategory(text)
                                viewModel.guitarResult.collect {
                                    if (it.isNotEmpty()) {
                                        adapter.submitList(it)
                                    } else {
                                        viewModel.accessoryResult.collect { list ->
                                            secondAdapter.submitList(list)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    return true
                }
            })

        binding.rvGuitarProducts.adapter = adapter
        binding.rvGuitarProducts.layoutManager = GridLayoutManager(context, 2)

        adapter.setOnClickListener(
            object : GenericClickListener<Guitar> {
                override fun onClick(position: Int, data: Guitar) {
                    bundle.putInt("id", data.id)
                    fragment.arguments = bundle
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_two, fragment).commit()
                }
            })

        binding.rvAccessoryProducts.adapter = secondAdapter
        binding.rvAccessoryProducts.layoutManager = GridLayoutManager(context, 2)

        secondAdapter.setOnClickListener(
            object : GenericClickListener<Accessory> {
                override fun onClick(position: Int, data: Accessory) {
                    bundle.putInt("number", data.id)
                    fragment.arguments = bundle
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_two, fragment).commit()
                }
            })
    }
}