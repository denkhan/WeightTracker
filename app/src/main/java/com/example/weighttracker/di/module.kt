package com.example.weighttracker.di

import androidx.room.Room
import com.example.weighttracker.coroutineUtils.CoroutineContextProvider
import com.example.weighttracker.repository.Repository
import com.example.weighttracker.repository.RepositoryImpl
import com.example.weighttracker.roomdb.AppDatabase
import com.example.weighttracker.viewmodels.WeightViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.applicationContext
import org.koin.dsl.module.module

val weightAppModule = module {
    single {
        Room
            .databaseBuilder(androidContext(), AppDatabase::class.java, "weight.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().weightDao() }
    single<Repository> { RepositoryImpl(get()) }
    viewModel { WeightViewModel(get(), CoroutineContextProvider()) }
}


