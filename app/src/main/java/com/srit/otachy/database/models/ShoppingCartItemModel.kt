package com.srit.otachy.database.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["itemId", "categoryId"])
data class  ShoppingCartItemModel(
    val itemId: String,
    val itemName: String,
    val totalPrice: Double,

    val categoryId: String,
    val categoryName: String,
    val numberOfItems: Double
) : Parcelable {

    override fun toString(): String {
        return "ShoppingCart(itemId='$itemId', itemName='$itemName', itemPrice=$totalPrice, categoryId='$categoryId', categoryName='$categoryName', numberOfItems=$numberOfItems)"
    }
}