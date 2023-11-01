package com.shop.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.shop.R
import com.shop.data.model.Guitar
import com.shop.databinding.FragmentCategoryBinding
import com.shop.presentation.adapters.GenericRecyclerAdapter
import com.shop.presentation.vm.ProductViewModel
import com.shop.utils.GenericClickListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val inputData = args?.getString("category")
        initDetailedCategoryRv(inputData)

        binding.categoryBackButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_two, SearchFragment()).commit()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.isLoading) {
                        binding.progressBarCategory.visibility = ProgressBar.VISIBLE
                    } else {
                        binding.progressBarCategory.visibility = ProgressBar.INVISIBLE
                    }
                    if (state.isError) {
                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun initDetailedCategoryRv(string: String?) {
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadGuitarByCategory(string!!)
                viewModel.loadGuitarByBrand(string)
                viewModel.loadAccessoryByCategory(string)
                viewModel.guitarResult.collect {
                    adapter.submitList(it)
                }
            }
        }
        binding.detailedCategoryRv.adapter = adapter
        binding.detailedCategoryRv.layoutManager = GridLayoutManager(context, 2)

    }
}
