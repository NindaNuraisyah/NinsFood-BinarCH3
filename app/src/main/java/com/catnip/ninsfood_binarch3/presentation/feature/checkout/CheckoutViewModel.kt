package com.catnip.ninsfood_binarch3.presentation.feature.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.catnip.ninsfood_binarch3.data.repository.CartRepository
import com.catnip.ninsfood_binarch3.data.repository.UserRepository
import com.catnip.ninsfood_binarch3.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val cartList = cartRepository.getUserCartData().asLiveData(Dispatchers.IO)

    private val _checkoutResult = MutableLiveData<ResultWrapper<Boolean>>()
    val checkoutResult: LiveData<ResultWrapper<Boolean>>
        get() = _checkoutResult

    fun deleteAllCarts() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAllCarts()
        }
    }

    fun createOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            val carts = cartList.value?.payload?.first ?: return@launch
            val total = cartList.value?.payload?.second ?: 0
            val username = userRepository.getCurrentUser()?.fullName.orEmpty()
            cartRepository.createOrder(carts, total, username).collect {
                _checkoutResult.postValue(it)
            }
        }
    }
}
