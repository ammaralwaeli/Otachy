package com.srit.otachy.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.srit.otachy.database.models.ShoppingCartItemModel
import com.srit.otachy.database.models.VendorShopModel

@Database(entities = [ShoppingCartItemModel::class,VendorShopModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingCartDao(): ShoppingCartDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "app_db")
                .build()
    }


}

