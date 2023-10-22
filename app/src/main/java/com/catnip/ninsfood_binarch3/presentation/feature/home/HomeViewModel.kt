package com.catnip.ninsfood_binarch3.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.catnip.ninsfood_binarch3.data.datasource.local.datastore.UserPreferenceDataSource
import com.catnip.ninsfood_binarch3.data.repository.ProductRepository
import com.catnip.ninsfood_binarch3.model.Categories
import com.catnip.ninsfood_binarch3.model.Product
import com.catnip.ninsfood_binarch3.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProductRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource
) : ViewModel() {

    fun setUsingGridPref(getUsingGrid : Boolean) {
        viewModelScope.launch { userPreferenceDataSource.setUsingGridPref(getUsingGrid) }
    }
    private val _categories = MutableLiveData<ResultWrapper<List<Categories>>>()
    val categories : LiveData<ResultWrapper<List<Categories>>>
        get() = _categories

    private val _products = MutableLiveData<ResultWrapper<List<Product>>>()
    val products : LiveData<ResultWrapper<List<Product>>>
        get() = _products

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories().collect{
                _categories.postValue(it)
            }
        }
    }

    fun getProducts(category: String? = null){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProducts(if(category == "all") null else category?.toLowerCase()).collect{
                _products.postValue(it)
            }
        }
    }

    val usingGridLiveData = userPreferenceDataSource.getUsingGridPrefFlow().asLiveData(
        Dispatchers.IO)
}