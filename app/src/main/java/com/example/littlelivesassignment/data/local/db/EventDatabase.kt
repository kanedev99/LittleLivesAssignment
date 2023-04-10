package com.example.littlelivesassignment.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.littlelivesassignment.data.local.DB
import com.example.littlelivesassignment.data.local.dao.EventDao
import com.example.littlelivesassignment.data.local.dao.RemoteKeysDao
import com.example.littlelivesassignment.data.local.entity.RemoteKeys
import com.example.littlelivesassignment.data.model.Event

@Database(entities = [Event::class, RemoteKeys::class], version = DB.DATABASE_VERSION, exportSchema = false)
abstract class EventDatabase: RoomDatabase() {

    abstract fun eventDao(): EventDao

    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: EventDatabase? = null

        fun getInstance(context: Context): EventDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): EventDatabase {
            return Room.databaseBuilder(context, EventDatabase::class.java, DB.DATABASE_NAME).build()
        }
    }
}