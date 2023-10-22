package com.catnip.ninsfood_binarch3.data.datasource.dummy

import com.catnip.ninsfood_binarch3.model.Product

interface DummyProductDataSource {
    fun getProductList(): List<Product>
}

class DummyProductDataSourceImpl() : DummyProductDataSource {
    override fun getProductList(): List<Product> = listOf(
        Product(
            name = "Fish Burger",
            price = 40000,
            priceFormat = "Rp. 40.000",
            imageUrl = "https://raw.githubusercontent.com/NindaNuraisyah/NinsFood/feature/feature_home/app/src/main/res/drawable/img_fish_burger.png",
            desc = "Fillet ikan garing di atas roti bundar empuk dengan saus dan pelengkap lezat. Rasa dan kenikmatan dalam satu gigitan!",
            location = "Jl. Dewi Sartika No.27, Kuningan, Kec. Kuningan, Kabupaten Kuningan, Jawa Barat 45511"
        ),
        Product(
            name = "Beef Burger",
            price = 45000,
            priceFormat = "Rp. 45.000",
            imageUrl = "https://raw.githubusercontent.com/NindaNuraisyah/NinsFood/feature/feature_home/app/src/main/res/drawable/img_beef_burger.png",
            desc = "Patty daging sapi lezat dalam roti burger dengan saus dan pelengkap menggugah selera. Nikmati kenikmatannya!",
            location = "Jl. Pramuka, Purwawinangun, Kec. Kuningan, Kabupaten Kuningan, Jawa Barat 45512"
        ),
        Product(
            name = "Chicken Burger",
            price = 20000,
            priceFormat = "Rp. 20.000",
            imageUrl = "https://raw.githubusercontent.com/NindaNuraisyah/NinsFood/feature/feature_home/app/src/main/res/drawable/img_chicken_burger.png",
            desc = "Selami sensasi rasa yang luar biasa dengan Chicken Supreme Burger kami! Patty ayam gurih, saus istimewa, dan pelengkap segar, semuanya dalam satu gigitan memikat.",
            location = "Jl. Ahmad Yani, Purwawinangun, Kec. Kuningan, Kabupaten Kuningan, Jawa Barat 45512"
        ),
        Product(
            name = "Garden Burger",
            price = 15000,
            priceFormat = "Rp. 15.000",
            imageUrl = "https://raw.githubusercontent.com/NindaNuraisyah/NinsFood/feature/feature_home/app/src/main/res/drawable/img_garden_burger.png",
            desc = "Temani lidah Anda dalam petualangan rasa dengan Garden Burger kami! Patty nabati yang lezat, saus unik, dan pelengkap segar, semua dalam satu gigitan yang memikat.",
            location = "Jl. Purwasari-Sindangagung, Purwasari, Kec. Garawangi, Kabupaten Kuningan, Jawa Barat 45571"
        ),
    )
}