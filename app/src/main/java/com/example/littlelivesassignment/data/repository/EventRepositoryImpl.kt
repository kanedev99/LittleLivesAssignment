package com.example.littlelivesassignment.data.repository

import com.example.littlelivesassignment.data.remote.ApiService
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(private val myService: ApiService): EventRepository {

}