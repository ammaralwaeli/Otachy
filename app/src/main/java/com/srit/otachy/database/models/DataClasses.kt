package com.srit.otachy.database.models
import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryItemModel(val categoryId:String, val image: Bitmap?, val title: String):Parcelable

//@Parcelize
//data class ServiceItemModel(val id:Int, val price: Int, val name: String):Parcelable

//@Parcelize
//data class MeatServiceItemModel(val id:Int, val price: Int, val name: String, val imageUrl:String):Parcelable

data class LoginModel(
    val mobileNumber:String,
    val password:String
)
data class Governments(
    val government:String
)
data class RegisterModel(
    val username:String,
    val password:String,
    val mobileNumber:String,
    val government:String,
    val district:String
)
data class VerificateionModel(
    val id:Int,
    val code:String
)