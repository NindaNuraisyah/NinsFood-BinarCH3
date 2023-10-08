package com.catnip.ninsfood_binarch3.data.datasource.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.catnip.ninsfood_binarch3.data.datasource.local.database.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM PRODUCTS")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM PRODUCTS WHERE id == :id")
    fun getProductById(id: Int): Flow<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: List<ProductEntity>)

    @Delete
    suspend fun deleteProduct(product: ProductEntity): Int

    @Update
    suspend fun updateProduct(product: ProductEntity): Int
}