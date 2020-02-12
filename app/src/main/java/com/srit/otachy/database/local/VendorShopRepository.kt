package com.srit.otachy.database.local

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.srit.otachy.database.models.ShoppingCartItemModel
import com.srit.otachy.database.models.VendorShopModel


class VendorShopRepository(context: Context){
    private val dao:ShoppingCartDao= AppDatabase.getInstance(context).shoppingCartDao()

    fun deleteItems(vararg items: VendorShopModel){
        DeleteVendorShopItemsAsyncTask(dao).execute(*items)
    }

    fun insertItems(vararg items: VendorShopModel){
        InsertVendorShopItemsAsyncTask(dao).execute(*items)
    }

    fun getItems(): LiveData<List<VendorShopModel>> = dao.getVendorss()
}


class DeleteVendorShopItemsAsyncTask (private val dao:ShoppingCartDao)
    : AsyncTask<VendorShopModel, Void, Void>() {

    override fun doInBackground(vararg params: VendorShopModel): Void? {
        dao.deleteItems(*params)
        return null
    }
}

class InsertVendorShopItemsAsyncTask(private val dao:ShoppingCartDao)
    :
    AsyncTask<VendorShopModel, Void, Void>() {
    override fun doInBackground(vararg params: VendorShopModel): Void?{
        dao.insertItems(*params)
        return null
    }
}
