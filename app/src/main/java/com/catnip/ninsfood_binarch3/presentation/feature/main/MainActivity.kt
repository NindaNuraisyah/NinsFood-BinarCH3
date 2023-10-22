package com.catnip.ninsfood_binarch3.presentation.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.catnip.ninsfood_binarch3.R
import com.catnip.ninsfood_binarch3.data.datasource.dummy.DummyCategoriesDataSourceImpl
import com.catnip.ninsfood_binarch3.data.datasource.dummy.DummyProductDataSourceImpl
import com.catnip.ninsfood_binarch3.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNav()
        val json = Gson().toJson(DummyProductDataSourceImpl().getProductList())
        val jsonca = Gson().toJson(DummyCategoriesDataSourceImpl().getProductCategories())
        Log.d("Main", json)
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }

}