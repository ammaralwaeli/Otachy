package com.srit.otachy.database.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["itemId", "categoryId"])
data class  ShoppingCartItemModel(
    val itemId: String,
    val vendorId: String,
    val itemName: String,
    val totalPrice: Int,
    val categoryId: String,
    val categoryName: String,
    val numberOfItems: Int
) : Parcelable {
    companion object Instance{
        lateinit var instance:ShoppingCartItemModel
        lateinit var orders:List<ShoppingCartItemModel>
    }
    override fun toString(): String {
        return "ShoppingCartItemModel(itemId='$itemId', vendorId='$vendorId', itemName='$itemName', totalPrice=$totalPrice, categoryId='$categoryId', categoryName='$categoryName', numberOfItems=$numberOfItems)"
    }
}

@Parcelize
@Entity(primaryKeys = ["vendorId"])
data class  VendorShopModel (
    val vendorId: Int,
    val vendorName: String,
    val vendorCity: String,
    val vendorUserId: String,
    val homeRecieve:Int
) : Parcelable {
    companion object Instance{
        lateinit var instance:VendorShopModel
    }
    override fun toString(): String {
        return "VendorShopModel(vendorId=$vendorId, vendorName='$vendorName', vendorCity='$vendorCity', vendorUserId='$vendorUserId', homeRecieve=$homeRecieve)"
    }


}