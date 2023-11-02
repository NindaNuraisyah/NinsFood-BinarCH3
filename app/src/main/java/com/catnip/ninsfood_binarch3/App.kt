package com.catnip.ninsfood_binarch3

import android.app.Application
import com.catnip.ninsfood_binarch3.data.datasource.local.database.AppDatabase
import com.catnip.ninsfood_binarch3.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App() : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(AppModules.modules)
        }
    }
}
