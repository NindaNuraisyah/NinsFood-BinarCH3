package com.catnip.ninsfood_binarch3.presentation.feature.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.catnip.ninsfood_binarch3.data.repository.CartRepository
import com.catnip.ninsfood_binarch3.model.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel(private val repo: CartRepository) : ViewModel() {

    val cartList = repo.getUserCartData().asLiveData(Dispatchers.IO)

    fun decreaseCart(item: Cart) {
        viewModelScope.launch {
            repo.decreaseCart(item).collect {
                Log.d("CartViewModel", " : Decrease Cart -> $it ${it.payload} ${it.exception}")
            }
        }
    }

    fun increaseCart(item: Cart) {
        viewModelScope.launch {
            repo.increaseCart(item).collect {
                Log.d("CartViewModel", " : Increase Cart -> $it ${it.payload} ${it.exception}")
            }
        }
    }

    fun deleteCart(item: Cart) {
        viewModelScope.launch {
            repo.deleteCart(item).collect {
                Log.d("CartViewModel", " : Remove Cart -> $it ${it.payload} ${it.exception}")
            }
        }
    }

    fun setCartNotes(item: Cart) {
        viewModelScope.launch { repo.setCartNotes(item).collect() }
    }

    fun isCartNotEmpty(): Boolean {
        return cartList.value?.payload?.first?.isNotEmpty() ?: false
    }
}
