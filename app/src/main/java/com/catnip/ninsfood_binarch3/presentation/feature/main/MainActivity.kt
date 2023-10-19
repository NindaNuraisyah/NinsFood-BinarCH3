package com.catnip.ninsfood_binarch3.presentation.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.catnip.ninsfood_binarch3.R
import com.catnip.ninsfood_binarch3.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.catnip.ninsfood_binarch3.data.repository.UserRepositoryImpl
import com.catnip.ninsfood_binarch3.databinding.ActivityMainBinding
import com.catnip.ninsfood_binarch3.presentation.feature.login.LoginActivity
import com.catnip.ninsfood_binarch3.utils.GenericViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }

}