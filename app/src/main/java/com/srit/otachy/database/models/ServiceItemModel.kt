package com.srit.otachy.database.models

import android.os.Parcel
import android.os.Parcelable


open class ServiceItemModel :Parcelable{
    val id:Int
    var price: Double
    val name: String


    constructor(parcel: Parcel){
        id= parcel.readInt()
        price= parcel.readDouble()
        name= parcel.readString()?:""
    }

    constructor(id: Int, price: Double, name: String)  {
        this.id = id
        this.price = price
        this.name = name
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeDouble(price)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ServiceItemModel> {
        override fun createFromParcel(parcel: Parcel): ServiceItemModel {
            return ServiceItemModel(parcel)
        }

        override fun newArray(size: Int): Array<ServiceItemModel?> {
            return arrayOfNulls(size)
        }
    }
}