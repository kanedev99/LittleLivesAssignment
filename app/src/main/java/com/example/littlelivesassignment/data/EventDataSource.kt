package com.example.littlelivesassignment.data

import androidx.paging.PagingSource
import com.example.littlelivesassignment.data.model.Event

abstract class EventDataSource: PagingSource<Int, Event>() {

}