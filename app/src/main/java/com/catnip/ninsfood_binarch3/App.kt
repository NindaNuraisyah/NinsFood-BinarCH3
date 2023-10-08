package com.catnip.ninsfood_binarch3

import android.app.Application
import com.catnip.ninsfood_binarch3.data.datasource.local.database.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}