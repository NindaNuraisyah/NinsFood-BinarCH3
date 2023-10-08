package com.catnip.ninsfood_binarch3.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.catnip.ninsfood_binarch3.data.datasource.local.datastore.UserPreferenceDataSource
import com.catnip.ninsfood_binarch3.data.repository.ProductRepository
import com.catnip.ninsfood_binarch3.model.Categories
import com.catnip.ninsfood_binarch3.model.Product
import com.catnip.ninsfood_binarch3.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: ProductRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource) : ViewModel() {
    fun setUsingGridPref(getUsingGrid : Boolean) {
        viewModelScope.launch { userPreferenceDataSource.setUsingGridPref(getUsingGrid) }
    }
    fun getCategoriesData(): List<Categories> {
        return repo.getCategories()
    }

    val usingGridLiveData = userPreferenceDataSource.getUsingGridPrefFlow().asLiveData(
        Dispatchers.IO)
    val productListLiveData = repo.getProducts().asLiveData(
        Dispatchers.IO)
}