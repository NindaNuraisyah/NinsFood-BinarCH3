package com.catnip.ninsfood_binarch3.data.repository

import com.catnip.ninsfood_binarch3.data.datasource.dummy.DummyCategoriesDataSource
import com.catnip.ninsfood_binarch3.data.datasource.local.database.datasource.ProductDataSource
import com.catnip.ninsfood_binarch3.data.datasource.local.database.mapper.toProductList
import com.catnip.ninsfood_binarch3.model.Categories
import com.catnip.ninsfood_binarch3.model.Product
import com.catnip.ninsfood_binarch3.utils.ResultWrapper
import com.catnip.ninsfood_binarch3.utils.proceed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


interface ProductRepository {
    fun getCategories(): List<Categories>
    fun getProducts(): Flow<ResultWrapper<List<Product>>>
}

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val dummyCategoriesDataSource: DummyCategoriesDataSource
) : ProductRepository {

    override fun getCategories(): List<Categories> {
        return dummyCategoriesDataSource.getProductCategories()
    }

    override fun getProducts(): Flow<ResultWrapper<List<Product>>> {
        return productDataSource.getAllProducts().map {
            proceed { it.toProductList() }
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }
}