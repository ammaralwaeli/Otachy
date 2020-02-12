package com.srit.otachy.database.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.srit.otachy.database.models.ShoppingCartItemModel
import com.srit.otachy.database.models.VendorShopModel

@Dao
interface ShoppingCartDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(vararg cartItems: ShoppingCartItemModel)

    // delete uses the primary key to delete items
    @Delete
    fun deleteItems(vararg cartItems: ShoppingCartItemModel)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItems(vararg cartItems: VendorShopModel)

    // delete uses the primary key to delete items
    @Delete
    fun deleteItems(vararg cartItems: VendorShopModel)


    @Query("SELECT * FROM shoppingCartItemModel WHERE shoppingCartItemModel.vendorId=:vendorId")
    fun getItems(vendorId:Int): LiveData<List<ShoppingCartItemModel>>


    @Query("SELECT * FROM vendorShopModel")
    fun getVendorss(): LiveData<List<VendorShopModel>>
}