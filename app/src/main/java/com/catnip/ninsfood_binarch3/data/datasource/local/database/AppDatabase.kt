package com.catnip.ninsfood_binarch3.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.catnip.ninsfood_binarch3.data.datasource.local.database.dao.CartDao
import com.catnip.ninsfood_binarch3.data.datasource.local.database.entity.CartEntity

@Database(
    entities = [CartEntity::class],
    version = 5,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private const val DB_NAME = "NinsFood-BinarCH3.db"

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}
