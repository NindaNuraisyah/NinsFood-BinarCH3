package com.catnip.ninsfood_binarch3.data.network.api.datasource

import com.catnip.ninsfood_binarch3.data.network.api.model.category.CategoriesResponse
import com.catnip.ninsfood_binarch3.data.network.api.model.order.OrderRequest
import com.catnip.ninsfood_binarch3.data.network.api.model.order.OrderResponse
import com.catnip.ninsfood_binarch3.data.network.api.model.product.ProductsResponse
import com.catnip.ninsfood_binarch3.data.network.api.service.NinsFoodApiService

interface NinsFoodDataSource {
    suspend fun getProducts(category: String? = null): ProductsResponse
    suspend fun getCategories(): CategoriesResponse
    suspend fun createOrder(orderRequest: OrderRequest): OrderResponse
}

class NinsFoodApiDataSource(private val service: NinsFoodApiService) : NinsFoodDataSource {
    override suspend fun getProducts(category: String?): ProductsResponse {
        return service.getProducts(category)
    }

    override suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }

    override suspend fun createOrder(orderRequest: OrderRequest): OrderResponse {
        return service.createOrder(orderRequest)
    }

}