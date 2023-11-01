package com.shop.room.accessories

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shop.data.model.Accessory


@Database(entities = [Accessory::class], version = 1, exportSchema = false)
abstract class AccessoriesDatabase : RoomDatabase(){

    abstract val accessoriesDao: AccessoriesDao
}