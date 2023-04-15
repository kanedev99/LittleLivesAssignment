package com.example.littlelivesassignment.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.littlelivesassignment.data.local.DB
import com.example.littlelivesassignment.data.local.converter.AnyTypeConverter
import com.example.littlelivesassignment.data.local.dao.EventDao
import com.example.littlelivesassignment.data.local.dao.RemoteKeysDao
import com.example.littlelivesassignment.data.local.entity.RemoteKeys
import com.example.littlelivesassignment.data.model.*

@Database(
    entities = [
        UserEvent::class,
        RemoteKeys::class,
        ChildEvent::class,
        ActivityEvent::class,
        AttendanceRecord::class,
        StoryPublishedEvent::class,
        PortfolioEvent::class,
        StoryExportedEvent::class,
        MsgParams::class,
        ReferenceObject::class,
    ],
    version = DB.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(AnyTypeConverter::class)
abstract class UserEventDatabase: RoomDatabase() {

    abstract fun eventDao(): EventDao

    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: UserEventDatabase? = null

        fun getInstance(context: Context): UserEventDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): UserEventDatabase {
            return Room.databaseBuilder(context, UserEventDatabase::class.java, DB.DATABASE_NAME).build()
        }
    }
}