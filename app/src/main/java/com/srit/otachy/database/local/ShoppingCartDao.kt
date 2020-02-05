package com.srit.otachy.database.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.srit.otachy.database.models.ShoppingCartItemModel

@Dao
interface ShoppingCartDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(vararg cartItems: ShoppingCartItemModel)

    // delete uses the primary key to delete items
    @Delete
    fun deleteItems(vararg cartItems: ShoppingCartItemModel)


    @Query("SELECT * FROM shoppingCartItemModel")
    fun getItems(): LiveData<List<ShoppingCartItemModel>>
}