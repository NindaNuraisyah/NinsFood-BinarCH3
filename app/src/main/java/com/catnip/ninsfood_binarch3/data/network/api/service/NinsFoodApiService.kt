package com.catnip.ninsfood_binarch3.data.network.api.service

import java.util.concurrent.TimeUnit
import com.catnip.ninsfood_binarch3.BuildConfig
import com.catnip.ninsfood_binarch3.data.network.api.model.category.CategoriesResponse
import com.catnip.ninsfood_binarch3.data.network.api.model.order.OrderRequest
import com.catnip.ninsfood_binarch3.data.network.api.model.order.OrderResponse
import com.catnip.ninsfood_binarch3.data.network.api.model.product.ProductsResponse
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NinsFoodApiService {

    @GET("listmenu")
    suspend fun getProducts(@Query("c") category: String? = null): ProductsResponse

    @GET("category")
    suspend fun getCategories(): CategoriesResponse

    @POST("order")
    suspend fun createOrder(@Body orderRequest: OrderRequest): OrderResponse

    companion object {
        @JvmStatic
        operator fun invoke(chucker: ChuckerInterceptor): NinsFoodApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(NinsFoodApiService::class.java)
        }
    }
}