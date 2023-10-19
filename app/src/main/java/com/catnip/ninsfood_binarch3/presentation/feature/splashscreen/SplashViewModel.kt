package com.catnip.ninsfood_binarch3.presentation.feature.splashscreen

import androidx.lifecycle.ViewModel
import com.catnip.ninsfood_binarch3.data.repository.UserRepository

class SplashViewModel(private val repository: UserRepository) : ViewModel() {
    fun isUserLoggedIn() = repository.isLoggedIn()
}