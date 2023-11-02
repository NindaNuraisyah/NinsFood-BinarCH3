package com.catnip.ninsfood_binarch3.data.network.api.model.category

import androidx.annotation.Keep
import com.catnip.ninsfood_binarch3.model.Categories
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryItemResponse(
    @SerializedName("image_url")
    val imgUrl: String?,
    @SerializedName("nama")
    val name: String?
)

fun CategoryItemResponse.toCategory() = Categories(
    imageUrl = this.imgUrl.orEmpty(),
    name = this.name.orEmpty()
)

fun Collection<CategoryItemResponse>.toCategoryList() = this.map {
    it.toCategory()
}
