package com.shop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.shop.databinding.ActivityContainerBinding
import com.shop.presentation.ui.HomeFragment
import com.shop.presentation.ui.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {

    private var _binding: ActivityContainerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_home -> replaceFragment(HomeFragment())
                R.id.item_search -> replaceFragment(SearchFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_two, fragment)
        fragmentTransaction.commit()
    }
}