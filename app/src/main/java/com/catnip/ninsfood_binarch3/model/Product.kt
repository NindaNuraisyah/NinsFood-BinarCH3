package com.catnip.ninsfood_binarch3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Int,
    val priceFormat: String,
    val imageUrl: String,
    val desc: String,
    val location: String
) : Parcelable
