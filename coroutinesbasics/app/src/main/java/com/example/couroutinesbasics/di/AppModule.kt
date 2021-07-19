package com.example.couroutinesbasics.di

import android.app.Application
import androidx.room.Room
import com.example.couroutinesbasics.data.TrumpQuotesDataBase
import com.example.couroutinesbasics.repository.TrumpQuoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(TrumpQuoteService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesRestaurantApi(retrofit: Retrofit): TrumpQuoteService =
        retrofit.create(TrumpQuoteService::class.java)



    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room
        .databaseBuilder(app, TrumpQuotesDataBase::class.java, "trump_database")
        .build()

    @Provides
    fun provideDao(
        dataBase: TrumpQuotesDataBase
    ) = dataBase.trumpDao()



}