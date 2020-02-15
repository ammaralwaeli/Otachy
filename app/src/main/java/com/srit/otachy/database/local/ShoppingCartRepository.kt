package com.srit.otachy.database.local

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.srit.otachy.database.models.ShoppingCartItemModel
import java.lang.Exception


class ShoppingCartRepository(context: Context) {
    private val dao: ShoppingCartDao = AppDatabase.getInstance(context).shoppingCartDao()
    private val ctx = context
    fun deleteItems(vararg items: ShoppingCartItemModel) {
        DeleteShoppingCartItemsAsyncTask(dao).execute(*items)
    }

    fun insertItems(vararg items: ShoppingCartItemModel) {

        var updated = false
        for (item in getItems(items[0].vendorId.toInt())) {

            if (item.itemId == items[0].itemId) {
                val newItem = ShoppingCartItemModel(
                    item.itemId,
                    item.vendorId,
                    item.itemName,
                    items[0].totalPrice + item.totalPrice,
                    item.categoryId,
                    item.categoryName,
                    item.numberOfItems + items[0].numberOfItems
                )

                InsertShoppingCartItemsAsyncTask(dao).execute(newItem)
                updated = true
                Toast.makeText(ctx, "updated", Toast.LENGTH_LONG).show()
                break
            }
        }
        if (!updated) {
            InsertShoppingCartItemsAsyncTask(dao).execute(*items)
            Toast.makeText(ctx, "inserted", Toast.LENGTH_LONG).show()
        }
    }

    fun getItems(vendorId: Int): List<ShoppingCartItemModel> {
        try {

            val list = SelectShoppingItemsAsyncTask(dao).execute(vendorId).get()
            return list
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }
}

class DeleteShoppingCartItemsAsyncTask(private val dao: ShoppingCartDao) :
    AsyncTask<ShoppingCartItemModel, Void, Void>() {

    override fun doInBackground(vararg params: ShoppingCartItemModel): Void? {
        dao.deleteItems(*params)
        return null
    }
}


class SelectShoppingItemsAsyncTask(private val dao: ShoppingCartDao) :
    AsyncTask<Int, Void, List<ShoppingCartItemModel>>() {
    override fun doInBackground(vararg params: Int?): List<ShoppingCartItemModel> {
        return params[0]?.let { dao.getItems(it) }!!
    }

}

class InsertShoppingCartItemsAsyncTask(private val dao: ShoppingCartDao) :
    AsyncTask<ShoppingCartItemModel, Void, Void>() {
    override fun doInBackground(vararg params: ShoppingCartItemModel): Void? {
        try {
            dao.insertItems(*params)
        } catch (e: Exception) {
            e.printStackTrace()

        }

        return null
    }
}