package com.srit.otachy.database.models

import android.os.Parcel
import android.os.Parcelable
import com.srit.otachy.helpers.BackendHelper

class MeatServiceItemModel: ServiceItemModel{
    private var img:String


    constructor(parcel: Parcel) : super(parcel) {
        img= parcel.readString()?:""
    }

    val fullImgUrl get() = (BackendHelper.IMAGE_URL+img).trim();

    constructor(id: Int, price: Double, name: String, imageUrl: String) : super(id, price, name) {
        this.img = imageUrl
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(img)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MeatServiceItemModel> {
        override fun createFromParcel(parcel: Parcel): MeatServiceItemModel {
            return MeatServiceItemModel(parcel)
        }

        override fun newArray(size: Int): Array<MeatServiceItemModel?> {
            return arrayOfNulls(size)
        }
    }
}
