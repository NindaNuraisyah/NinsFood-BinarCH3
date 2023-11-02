package com.catnip.ninsfood_binarch3.di

import com.catnip.ninsfood_binarch3.data.datasource.local.database.AppDatabase
import com.catnip.ninsfood_binarch3.data.datasource.local.database.datasource.CartDataSource
import com.catnip.ninsfood_binarch3.data.datasource.local.database.datasource.CartDatabaseDataSource
import com.catnip.ninsfood_binarch3.data.datasource.local.datastore.UserPreferenceDataSource
import com.catnip.ninsfood_binarch3.data.datasource.local.datastore.UserPreferenceDataSourceImpl
import com.catnip.ninsfood_binarch3.data.datasource.local.datastore.appDataStore
import com.catnip.ninsfood_binarch3.data.network.api.datasource.NinsFoodApiDataSource
import com.catnip.ninsfood_binarch3.data.network.api.datasource.NinsFoodDataSource
import com.catnip.ninsfood_binarch3.data.network.api.service.NinsFoodApiService
import com.catnip.ninsfood_binarch3.data.network.firebase.auth.FirebaseAuthDataSource
import com.catnip.ninsfood_binarch3.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.catnip.ninsfood_binarch3.data.repository.CartRepository
import com.catnip.ninsfood_binarch3.data.repository.CartRepositoryImpl
import com.catnip.ninsfood_binarch3.data.repository.ProductRepository
import com.catnip.ninsfood_binarch3.data.repository.ProductRepositoryImpl
import com.catnip.ninsfood_binarch3.data.repository.UserRepository
import com.catnip.ninsfood_binarch3.data.repository.UserRepositoryImpl
import com.catnip.ninsfood_binarch3.presentation.feature.cart.CartViewModel
import com.catnip.ninsfood_binarch3.presentation.feature.checkout.CheckoutViewModel
import com.catnip.ninsfood_binarch3.presentation.feature.detailproduct.DetailProductViewModel
import com.catnip.ninsfood_binarch3.presentation.feature.home.HomeViewModel
import com.catnip.ninsfood_binarch3.presentation.feature.login.LoginViewModel
import com.catnip.ninsfood_binarch3.presentation.feature.profile.ProfileViewModel
import com.catnip.ninsfood_binarch3.presentation.feature.register.RegisterViewModel
import com.catnip.ninsfood_binarch3.presentation.feature.splashscreen.SplashViewModel
import com.catnip.ninsfood_binarch3.utils.AssetWrapper
import com.catnip.ninsfood_binarch3.utils.PreferenceDataStoreHelper
import com.catnip.ninsfood_binarch3.utils.PreferenceDataStoreHelperImpl
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val localModule = module {
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().cartDao() }
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { NinsFoodApiService.invoke(get()) }
        single { FirebaseAuth.getInstance() }
    }

    private val dataSourceModule = module {
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<NinsFoodDataSource> { NinsFoodApiDataSource(get()) }
        single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        single<CartRepository> { CartRepositoryImpl(get(), get()) }
        single<ProductRepository> { ProductRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::CartViewModel)
        viewModel { params -> DetailProductViewModel(params.get(), get()) }
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::SplashViewModel)
    }

    private val utilsModule = module {
        single { AssetWrapper(androidContext()) }
    }

    val modules: List<Module> = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule,
        utilsModule
    )
}
