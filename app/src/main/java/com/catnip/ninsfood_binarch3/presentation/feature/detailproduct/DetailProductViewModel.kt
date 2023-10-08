package com.catnip.ninsfood_binarch3.presentation.feature.detailproduct

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.ninsfood_binarch3.data.repository.CartRepository
import com.catnip.ninsfood_binarch3.model.Product
import com.catnip.ninsfood_binarch3.utils.ResultWrapper
import kotlinx.coroutines.launch

class DetailProductViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository
) : ViewModel() {

    val product = extras?.getParcelable<Product>(DetailProductActivity.EXTRA_PRODUCT)

    val productCountLiveData = MutableLiveData<Int>().apply {
        postValue(0)
    }

    val priceLiveData = MutableLiveData<Double>().apply {
        postValue(0.0)
    }

    private val _addToCartResult = MutableLiveData<ResultWrapper<Boolean>>()
    val addToCartResult: LiveData<ResultWrapper<Boolean>>
        get() = _addToCartResult

    val navigateToMapsLiveData = MutableLiveData<String?>()

    fun onLocationClicked() {
        val location = product?.location
        navigateToMapsLiveData.value = location
    }

    fun add() {
        val count = (productCountLiveData.value ?: 0) + 1
        productCountLiveData.postValue(count)
        priceLiveData.postValue(product?.price?.times(count) ?: 0.0)
    }

    fun minus() {
        if((productCountLiveData.value ?: 0) > 0){
            val count = (productCountLiveData.value ?: 0) -1
            productCountLiveData.postValue(count)
            priceLiveData.postValue(product?.price?.times(count) ?: 0.0)
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            val productQuantity =
                if ((productCountLiveData.value ?: 0) <= 0) 1 else productCountLiveData.value ?: 0
            product?.let {
                cartRepository.createCart(it, productQuantity).collect { result ->
                    _addToCartResult.postValue(result)
                }
            }
        }
    }
}