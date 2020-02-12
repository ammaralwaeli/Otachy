package com.srit.otachy.database.local

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.srit.otachy.database.models.ShoppingCartItemModel
import com.srit.otachy.database.models.VendorShopModel


class ShoppingCartRepository(context: Context){
    private val dao:ShoppingCartDao= AppDatabase.getInstance(context).shoppingCartDao()

    fun deleteItems(vararg items: ShoppingCartItemModel){
        DeleteShoppingCartItemsAsyncTask(dao).execute(*items)
    }

    fun insertItems(vararg items: ShoppingCartItemModel){
        InsertShoppingCartItemsAsyncTask(dao).execute(*items)
    }

    fun getItems(vendorId:Int): LiveData<List<ShoppingCartItemModel>> = dao.getItems(vendorId)
}

class DeleteShoppingCartItemsAsyncTask (private val dao:ShoppingCartDao)
    : AsyncTask<ShoppingCartItemModel, Void, Void>() {

    override fun doInBackground(vararg params: ShoppingCartItemModel): Void? {
        dao.deleteItems(*params)
        return null
    }
}

class InsertShoppingCartItemsAsyncTask(private val dao:ShoppingCartDao)
    :
    AsyncTask<ShoppingCartItemModel, Void, Void>() {
    override fun doInBackground(vararg params: ShoppingCartItemModel): Void?{
        dao.insertItems(*params)
        return null
    }
}