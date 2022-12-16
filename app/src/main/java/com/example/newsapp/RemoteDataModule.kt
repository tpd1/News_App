package com.example.newsapp

import com.example.newsapp.database.NewsAPIHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

//Instructions for Dagger to create Retrofit instance and NewsAPIHandler.
@Module
@InstallIn(SingletonComponent::class) //We only need one instance for the lifetime of the app.
object RemoteDataModule {

    // Specifies how to inject a news api handler class when needed.
    @Singleton
    @Provides
    fun provideNewsAPIHandler(rf: Retrofit): NewsAPIHandler {
        return rf.create(NewsAPIHandler::class.java)
    }

    // Specifies how to create a retrofit instance, needed in the method above.
    @Singleton // Only want one instance to exist
    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.ROOT_API_URL)
            .client(httpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }


}