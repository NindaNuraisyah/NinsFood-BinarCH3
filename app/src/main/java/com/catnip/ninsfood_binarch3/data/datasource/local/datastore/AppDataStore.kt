package com.catnip.ninsfood_binarch3.data.datasource.local.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.appDataStore by preferencesDataStore(
    // TODO : Change with your own preference app name
    name = "NinsFood-BinarCH3"
)
