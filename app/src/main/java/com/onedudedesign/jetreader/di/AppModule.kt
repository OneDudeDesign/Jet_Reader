package com.onedudedesign.jetreader.di

import com.google.firebase.firestore.FirebaseFirestore
import com.onedudedesign.jetreader.network.BooksApi
import com.onedudedesign.jetreader.repository.FireRepository
import com.onedudedesign.jetreader.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFireRepository()
    = FireRepository(queryBook = FirebaseFirestore.getInstance()
        .collection("books"))

    @Singleton
    @Provides
    fun provideBookApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()

    }

}