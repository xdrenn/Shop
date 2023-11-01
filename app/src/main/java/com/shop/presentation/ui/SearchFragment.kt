package com.shop.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.shop.R
import com.shop.databinding.FragmentSearchBinding
import com.shop.presentation.adapters.TwoGenericsRecyclerAdapter
import com.shop.utils.GenericClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var bundle = Bundle()

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
        initSearchView()
    }

    private fun initCategoryRecyclerView() {
        val recyclerView = binding.rvCategory
        val categoryFragment = CategoryFragment()
        val bindingInterface =
            object : TwoGenericsRecyclerAdapter.TwoGenericsRecyclerBindingInterface<Int, String> {
                override fun bindData(
                    item: Int,
                    secondItem: String,
                    view: View,
                    clickListener: GenericClickListener<Int>?,
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
        adapter.setOnClickListener(object : GenericClickListener<Int> {
            override fun onClick(position: Int, data: Int) {
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        if (position == 0) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.nav_host_fragment_two, categoryFragment).commit()
                            bundle.putString("category", "electric")
                            categoryFragment.arguments = bundle

                        }
                        if (position == 1) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.nav_host_fragment_two, categoryFragment).commit()
                            bundle.putString("category", "acoustic")
                            categoryFragment.arguments = bundle

                        }
                        if (position == 2) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.nav_host_fragment_two, categoryFragment).commit()
                            bundle.putString("category", "bass")
                            categoryFragment.arguments = bundle

                        }
                        if (position == 3) {
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.nav_host_fragment_two, categoryFragment).commit()
                            bundle.putString("category", "accessories")
                            categoryFragment.arguments = bundle
                        }
                    }
                }
            }
        })
    }

    private fun initSearchView() {
        val fragment = ProductsFragment()

        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_two, fragment).commit()
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    bundle.putString("data", text)
                    fragment.arguments = bundle
                }
                return true
            }
        })
    }
}