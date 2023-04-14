package com.example.littlelivesassignment.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.littlelivesassignment.data.model.UserEvent

@Dao
interface EventDao {

    @Query("SELECT * FROM event_list")
    fun getEventList(): PagingSource<Int, UserEvent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userEvents: List<UserEvent>)

    @Query("DELETE FROM event_list")
    suspend fun clearEvents()
}