package com.catnip.ninsfood_binarch3.data.datasource.local.database.datasource

import com.catnip.ninsfood_binarch3.data.datasource.local.database.dao.ProductDao
import com.catnip.ninsfood_binarch3.data.datasource.local.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductDataSource {

    fun getAllProducts(): Flow<List<ProductEntity>>

    fun getProductById(id: Int): Flow<ProductEntity>

    suspend fun insertProducts(product: List<ProductEntity>)

    suspend fun deleteProduct(product: ProductEntity): Int

    suspend fun updateProduct(product: ProductEntity): Int

}

class ProductDatabaseDataSource(private val dao : ProductDao) : ProductDataSource {
    override fun getAllProducts(): Flow<List<ProductEntity>> {
        return dao.getAllProducts()
    }

    override fun getProductById(id: Int): Flow<ProductEntity> {
        return dao.getProductById(id)
    }

    override suspend fun insertProducts(product: List<ProductEntity>) {
        return dao.insertProduct(product)
    }

    override suspend fun deleteProduct(product: ProductEntity): Int {
        return dao.deleteProduct(product)
    }

    override suspend fun updateProduct(product: ProductEntity): Int {
        return dao.updateProduct(product)
    }

}