package com.catnip.ninsfood_binarch3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Double,
    val imgUrl: String,
    val desc: String,
    val location: String
) : Parcelable
