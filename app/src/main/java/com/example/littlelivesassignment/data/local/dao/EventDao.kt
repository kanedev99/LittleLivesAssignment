package com.example.littlelivesassignment.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.littlelivesassignment.data.model.Event

@Dao
interface EventDao {

    @Query("SELECT * FROM event_list")
    fun getEventList(): PagingSource<Int, Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<Event>)

    @Query("DELETE FROM event_list")
    suspend fun clearEvents()
}