package com.catnip.ninsfood_binarch3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int? = null,
    val name: String,
    val price: Double,
    val imgUrl: String,
    val desc: String,
    val location: String
) : Parcelable

