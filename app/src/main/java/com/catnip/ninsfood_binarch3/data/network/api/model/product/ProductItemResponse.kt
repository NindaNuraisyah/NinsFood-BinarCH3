package com.catnip.ninsfood_binarch3.data.network.api.model.product

import androidx.annotation.Keep
import com.catnip.ninsfood_binarch3.model.Product
import com.google.gson.annotations.SerializedName

@Keep
data class ProductItemResponse(
    @SerializedName("nama")
    val name: String?,
    @SerializedName("harga")
    val price: Int?,
    @SerializedName("harga_format")
    val priceFormat: String,
    @SerializedName("detail")
    val desc: String?,
    @SerializedName("alamat_resto")
    val location: String?,
    @SerializedName("image_url")
    val imageUrl: String?
)

fun ProductItemResponse.toProduct() = Product(
    name = this.name.orEmpty(),
    price = this.price ?: 0,
    priceFormat = this.priceFormat,
    desc = this.desc.orEmpty(),
    location = this.location.orEmpty(),
    imageUrl = this.imageUrl.orEmpty()
)

fun Collection<ProductItemResponse>.toProductList() = this.map { it.toProduct() }
