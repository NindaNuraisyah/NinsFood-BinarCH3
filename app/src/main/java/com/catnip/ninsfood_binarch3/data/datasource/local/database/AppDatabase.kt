package com.catnip.ninsfood_binarch3.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.catnip.ninsfood_binarch3.data.datasource.dummy.DummyProductDataSourceImpl
import com.catnip.ninsfood_binarch3.data.datasource.local.database.AppDatabase.Companion.getInstance
import com.catnip.ninsfood_binarch3.data.datasource.local.database.dao.CartDao
import com.catnip.ninsfood_binarch3.data.datasource.local.database.dao.ProductDao
import com.catnip.ninsfood_binarch3.data.datasource.local.database.entity.CartEntity
import com.catnip.ninsfood_binarch3.data.datasource.local.database.entity.ProductEntity
import com.catnip.ninsfood_binarch3.data.datasource.local.database.mapper.toProductEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Database(
    entities = [CartEntity::class, ProductEntity::class],
    version = 2,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun productDao(): ProductDao

    companion object {
        private const val DB_NAME = "NinsFood-BinarCH3.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .addCallback(DatabaseSeederCallback(context))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

class DatabaseSeederCallback(private val context: Context) : RoomDatabase.Callback() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch {
            getInstance(context).productDao().insertProduct(prepopulateProducts())
            getInstance(context).cartDao().insertCarts(prepopulateCarts())
        }
    }

    private fun prepopulateProducts(): List<ProductEntity> {
        return DummyProductDataSourceImpl().getProductList().toProductEntity()
    }

    private fun prepopulateCarts(): List<CartEntity> {
        return mutableListOf(
            CartEntity(
                id = 1,
                productId = 1,
                itemNotes = "Tanpa Keju yaa",
                itemQuantity = 2
            ),
            CartEntity(
                id = 2,
                productId = 2,
                itemNotes = "Pedas bangett yaa",
                itemQuantity = 5
            ),
        )
    }
}