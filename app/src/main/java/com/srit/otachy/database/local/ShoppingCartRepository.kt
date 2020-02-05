package com.srit.otachy.database.local

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.srit.otachy.database.models.ShoppingCartItemModel


class ShoppingCartRepository(context: Context){
    private val dao:ShoppingCartDao= AppDatabase.getInstance(context).shoppingCartDao()

    fun deleteItems(vararg items: ShoppingCartItemModel){
        DeleteShoppingCartItemsAsyncTask(dao).execute(*items)
    }

    fun insertItems(vararg items: ShoppingCartItemModel){
        InsertShoppingCartItemsAsyncTask(dao).execute(*items)
    }

    fun getItems(): LiveData<List<ShoppingCartItemModel>> = dao.getItems()
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
//        dao.insertItems(
//                ShoppingCartItemModel("1", "itemName", 2, "1", "categoryName", 5),
//                ShoppingCartItemModel("2", "itemName1", 4, "2", "categoryName1", 5),
//                ShoppingCartItemModel("3", "itemName2", 5, "3", "categoryName2", 4),
//                ShoppingCartItemModel("4", "itemName3", 1, "4", "categoryName3", 2),
//                ShoppingCartItemModel("5", "itemName4", 8, "5", "categoryName4", 5),
//                ShoppingCartItemModel("6", "itemName5", 6, "6", "categoryName5", 1),
//                ShoppingCartItemModel("7", "itemName6", 4, "7", "categoryName6", 5)
//            )

        dao.insertItems(*params)

        return null
    }
}