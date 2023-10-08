package com.catnip.ninsfood_binarch3.data.datasource.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.catnip.ninsfood_binarch3.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {
    suspend fun getUsingGridPref(): Boolean
    fun getUsingGridPrefFlow(): Flow<Boolean>
    suspend fun setUsingGridPref(isUsingGrid: Boolean)
}

class UserPreferenceDataSourceImpl(private val preferenceHelper: PreferenceDataStoreHelper) :
    UserPreferenceDataSource {

    override suspend fun getUsingGridPref(): Boolean {
        return preferenceHelper.getFirstPreference(PREF_USING_GRID, false)
    }

    override fun getUsingGridPrefFlow(): Flow<Boolean> {
        return preferenceHelper.getPreference(PREF_USING_GRID, false)
    }

    override suspend fun setUsingGridPref(isUsingGrid: Boolean) {
        return preferenceHelper.putPreference(PREF_USING_GRID, isUsingGrid)
    }

    companion object {
        val PREF_USING_GRID = booleanPreferencesKey("PREF_USING_GRID")
    }
}