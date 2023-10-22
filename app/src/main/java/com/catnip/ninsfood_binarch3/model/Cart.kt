package com.catnip.ninsfood_binarch3.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.catnip.ninsfood_binarch3.data.network.api.model.order.OrderItemRequest

data class Cart(
    var id: Int? = null,
    var productId : String,
    var productName : String,
    var productPrice : Int,
    var itemQuantity: Int = 0,
    var productImgUrl: String,
    var itemNotes: String? = null,
)

fun Cart.toOrderItemRequest() = OrderItemRequest(
    notes = this.itemNotes.orEmpty(),
    price = this.productPrice,
    name = this.productName,
    qty = this.itemQuantity
)

fun Collection<Cart>.toOrderItemRequestList() = this.map { it.toOrderItemRequest() }
