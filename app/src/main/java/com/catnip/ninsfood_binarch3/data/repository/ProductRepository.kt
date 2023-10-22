package com.catnip.ninsfood_binarch3.data.repository

import com.catnip.ninsfood_binarch3.data.network.api.datasource.NinsFoodDataSource
import com.catnip.ninsfood_binarch3.data.network.api.model.category.toCategoryList
import com.catnip.ninsfood_binarch3.data.network.api.model.product.toProductList
import com.catnip.ninsfood_binarch3.model.Categories
import com.catnip.ninsfood_binarch3.model.Product
import com.catnip.ninsfood_binarch3.utils.ResultWrapper
import com.catnip.ninsfood_binarch3.utils.proceedFlow
import kotlinx.coroutines.flow.Flow


interface ProductRepository {
    fun getCategories(): Flow<ResultWrapper<List<Categories>>>
    fun getProducts(category: String? = null): Flow<ResultWrapper<List<Product>>>
}

class ProductRepositoryImpl(
    private val apiDataSource: NinsFoodDataSource,
) : ProductRepository {

    override fun getCategories(): Flow<ResultWrapper<List<Categories>>> {
        return proceedFlow {
            apiDataSource.getCategories().data?.toCategoryList() ?: emptyList()
        }
    }

    override fun getProducts(categories: String?): Flow<ResultWrapper<List<Product>>> {
        return proceedFlow {
            apiDataSource.getProducts(categories).data?.toProductList() ?: emptyList()
        }
    }
}