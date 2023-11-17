package com.catnip.ninsfood_binarch3.presentation.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.catnip.ninsfood_binarch3.data.datasource.local.datastore.UserPreferenceDataSource
import com.catnip.ninsfood_binarch3.data.repository.ProductRepository
import com.catnip.ninsfood_binarch3.tools.MainCoroutineRule
import com.catnip.ninsfood_binarch3.utils.AssetWrapper
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

class HomeViewModelTest {

    @MockK
    private lateinit var repo: ProductRepository

    @MockK
    private lateinit var userPreferenceDataSource: UserPreferenceDataSource

    @MockK
    private lateinit var assetWrapper: AssetWrapper

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }
}
