package com.shop.room.guitar

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shop.data.model.Guitar

@Database(entities = [Guitar::class], version = 1, exportSchema = false)
abstract class GuitarDatabase: RoomDatabase() {

    abstract val guitarDao: GuitarDao

}