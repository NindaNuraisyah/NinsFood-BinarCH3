package com.catnip.ninsfood_binarch3.model

import com.catnip.ninsfood_binarch3.data.network.api.model.order.OrderItemRequest

data class OrderItem(
    val notes: String,
    val price: Int,
    val name: String,
    val qty: Int
)

fun OrderItem.toOrderItemRequest() = OrderItemRequest(
    notes,
    price,
    name,
    qty
)
