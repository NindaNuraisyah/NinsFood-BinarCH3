package com.catnip.ninsfood_binarch3.data.datasource.local.database.datasource

import app.cash.turbine.test
import com.catnip.ninsfood_binarch3.data.datasource.local.database.dao.CartDao
import com.catnip.ninsfood_binarch3.data.datasource.local.database.entity.CartEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartDatabaseDataSourceTest {

    @MockK
    lateinit var cartDao: CartDao
    private lateinit var cartDataSource: CartDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        cartDataSource = CartDatabaseDataSource(cartDao)
    }

    @Test
    fun getAllCarts() {
        val itemEntityMock1 = mockk<CartEntity>()
        val itemEntityMock2 = mockk<CartEntity>()
        val listEntityMock = listOf(itemEntityMock1, itemEntityMock2)
        val flowMock = flow {
            emit(listEntityMock)
        }
        coEvery { cartDao.getAllCarts() } returns flowMock
        runTest {
            cartDataSource.getAllCarts().test {
                val result = awaitItem()
                assertEquals(listEntityMock, result)
                assertEquals(itemEntityMock1, result[0])
                assertEquals(itemEntityMock2, result[1])
                assertEquals(listEntityMock.size, result.size)
                awaitComplete()
            }
        }
    }

    @Test
    fun getCartById() {
        val mockItemEntity = mockk<CartEntity>()
        val flowMock = flow {
            emit(mockItemEntity)
        }
        coEvery { cartDao.getCartById(any()) } returns flowMock
        runTest {
            cartDataSource.getCartById(1).test {
                assertEquals(mockItemEntity, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun insertCart() {
        runTest {
            val mockCartEntity = mockk<CartEntity>()
            coEvery { cartDao.insertCart(any()) } returns 1
            val result = cartDataSource.insertCart(mockCartEntity)
            coEvery { cartDao.insertCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun deleteCart() {
        runTest {
            val mockCartEntity = mockk<CartEntity>()
            coEvery { cartDao.deleteCart(any()) } returns 1
            val result = cartDataSource.deleteCart(mockCartEntity)
            coEvery { cartDao.deleteCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun updateCart() {
        runTest {
            val mockCartEntity = mockk<CartEntity>()
            coEvery { cartDao.updateCart(any()) } returns 1
            val result = cartDataSource.updateCart(mockCartEntity)
            coEvery { cartDao.updateCart(any()) }
            assertEquals(result, 1)
        }
    }

    @Test
    fun deleteAllCarts() {
        runTest {
            coEvery { cartDao.deleteAllCarts() } returns Unit
            val result = cartDataSource.deleteAllCarts()
            coEvery { cartDao.deleteAllCarts() }
            assertEquals(result, Unit)
        }
    }
}
