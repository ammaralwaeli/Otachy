package com.srit.otachy.database.local

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.srit.otachy.database.models.VendorShopModel


class VendorShopRepository(context: Context){
    private val dao:ShoppingCartDao= AppDatabase.getInstance(context).shoppingCartDao()
    val ctx=context
    fun deleteItems(vararg items: VendorShopModel){
        DeleteVendorShopItemsAsyncTask(dao).execute(*items)
    }

    fun insertItems(vararg items: VendorShopModel){
        InsertVendorShopItemsAsyncTask(dao).execute(*items)
        Toast.makeText(ctx,"inserted",Toast.LENGTH_LONG).show()
    }

    fun getItems(): List<VendorShopModel>{

        try {
            val list = SelectVendorShopItemsAsyncTask(dao).execute()
            return list.get()
        }
        catch (e:Exception){
            e.printStackTrace()
            return listOf()
        }

    }
}


class DeleteVendorShopItemsAsyncTask (private val dao:ShoppingCartDao)
    : AsyncTask<VendorShopModel, Void, Void>() {

    override fun doInBackground(vararg params: VendorShopModel): Void? {
        dao.deleteItems(*params)
        return null
    }
}




class SelectVendorShopItemsAsyncTask(private val dao:ShoppingCartDao)
    :
    AsyncTask<Void, Void, List<VendorShopModel>>() {
    override fun doInBackground(vararg p0: Void?): List<VendorShopModel>{
        return dao.getVendors()
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
