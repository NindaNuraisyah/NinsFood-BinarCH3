package com.catnip.ninsfood_binarch3.data.datasource.dummy

import com.catnip.ninsfood_binarch3.model.Categories

interface DummyCategoriesDataSource {
    fun getProductCategories(): List<Categories>
}

class DummyCategoriesDataSourceImpl : DummyCategoriesDataSource {
    override fun getProductCategories(): List<Categories> = listOf(
        Categories(
            name = "Burger",
            imgUrl = "https://raw.githubusercontent.com/NindaNuraisyah/NinsFood/main/app/src/main/res/drawable/ic_burger_category.png"
        ),
        Categories(
            name = "Noodle",
            imgUrl = "https://raw.githubusercontent.com/NindaNuraisyah/NinsFood/main/app/src/main/res/drawable/ic_noodle_category.png"
        ),
        Categories(
            name = "Juice",
            imgUrl = "https://raw.githubusercontent.com/NindaNuraisyah/NinsFood/main/app/src/main/res/drawable/ic_juice_category.png"
        ),
        Categories(
            name = "Snack",
            imgUrl = "https://raw.githubusercontent.com/NindaNuraisyah/NinsFood/main/app/src/main/res/drawable/ic_snack_category.png"
        ),
        Categories(
            name = "Chicken",
            imgUrl = "https://raw.githubusercontent.com/NindaNuraisyah/NinsFood/main/app/src/main/res/drawable/ic_chicken.png"
        ),
        Categories(
            name = "Sushi",
            imgUrl = "https://raw.githubusercontent.com/NindaNuraisyah/NinsFood/main/app/src/main/res/drawable/ic_sushi.png"
        )
    )
}