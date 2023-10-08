package com.catnip.ninsfood_binarch3.data.datasource.local.database.mapper

import com.catnip.ninsfood_binarch3.data.datasource.local.database.entity.ProductEntity
import com.catnip.ninsfood_binarch3.model.Product

fun ProductEntity?.toProduct() = Product(
    id = this?.id ?: 0,
    name = this?.name.orEmpty(),
    price = this?.price ?: 0.0,
    desc = this?.desc.orEmpty(),
    imgUrl = this?.imgUrl.orEmpty(),
    location = this?.location.orEmpty(),
)

fun Product?.toProductEntity() = ProductEntity(
    name = this?.name.orEmpty(),
    price = this?.price ?: 0.0,
    desc = this?.desc.orEmpty(),
    imgUrl = this?.imgUrl.orEmpty(),
    location = this?.location.orEmpty(),
)

fun List<ProductEntity?>.toProductList() = this.map { it.toProduct() }
fun List<Product?>.toProductEntity() = this.map { it.toProductEntity() }