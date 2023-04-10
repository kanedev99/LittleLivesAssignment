package com.example.littlelivesassignment.di

import com.example.littlelivesassignment.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)
}