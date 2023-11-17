package com.catnip.ninsfood_binarch3.data.network.api.datasource

import com.catnip.ninsfood_binarch3.data.network.api.model.category.CategoriesResponse
import com.catnip.ninsfood_binarch3.data.network.api.model.order.OrderRequest
import com.catnip.ninsfood_binarch3.data.network.api.model.order.OrderResponse
import com.catnip.ninsfood_binarch3.data.network.api.model.product.ProductsResponse
import com.catnip.ninsfood_binarch3.data.network.api.service.NinsFoodApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NinsFoodApiDataSourceTest {

    @MockK
    lateinit var service: NinsFoodApiService

    private lateinit var dataSource: NinsFoodDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = NinsFoodApiDataSource(service)
    }

    @Test
    fun getProducts() {
        runTest {
            val mockResponse = mockk<ProductsResponse>(relaxed = true)
            coEvery { service.getProducts(any()) } returns mockResponse
            val response = dataSource.getProducts("makanan")
            coVerify { service.getProducts(any()) } // memverifikasi apakah service sudah terpanggil
            assertEquals(response, mockResponse) // mencocokan hasil antara actual dengan expected
        }
    }

    @Test
    fun getCategories() {
        runTest {
            val mockResponse = mockk<CategoriesResponse>(relaxed = true)
            coEvery { service.getCategories() } returns mockResponse
            val response = dataSource.getCategories()
            coVerify { service.getCategories() } // memverifikasi apakah service sudah terpanggil
            assertEquals(response, mockResponse) // mencocokan hasil antara actual dengan expected
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockResponse = mockk<OrderResponse>(relaxed = true)
            val mockRequest = mockk<OrderRequest>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val response = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) } // memverifikasi apakah service sudah terpanggil
            assertEquals(response, mockResponse) // mencocokan hasil antara actual dengan expected
        }
    }
}
