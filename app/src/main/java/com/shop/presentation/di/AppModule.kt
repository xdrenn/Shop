package com.shop.presentation.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.shop.data.remote.ApiService
import com.shop.data.repository.AccessoryRepository
import com.shop.data.repository.AccessoryRepositoryImpl
import com.shop.data.repository.GuitarRepository
import com.shop.data.repository.GuitarRepositoryImpl
import com.shop.data.repository.UserRepository
import com.shop.data.repository.UserRepositoryImpl
import com.shop.room.accessories.AccessoriesDatabase
import com.shop.room.accessories.AccessoriesRoomRepository
import com.shop.room.accessories.AccessoriesRoomRepositoryImpl
import com.shop.room.guitar.GuitarDatabase
import com.shop.room.guitar.GuitarRoomRepository
import com.shop.room.guitar.GuitarRoomRepositoryImpl
import com.shop.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideAPIService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): ApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService, preferences: SharedPreferences): UserRepository {
        return UserRepositoryImpl(apiService, preferences)
    }

    @Singleton
    @Provides
    fun provideGuitarRepository(
        apiService: ApiService,
        preferences: SharedPreferences
    ): GuitarRepository {
        return GuitarRepositoryImpl(apiService, preferences)
    }

    @Provides
    @Singleton
    fun provideDataBase(app: Application): GuitarDatabase {
        return Room.databaseBuilder(
            app,
            GuitarDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDbRepository(db: GuitarDatabase): GuitarRoomRepository {
        return GuitarRoomRepositoryImpl(db.guitarDao)
    }

    @Provides
    @Singleton
    fun provideAccessoriesRepository(
        apiService: ApiService,
        preferences: SharedPreferences
    ): AccessoryRepository {
        return AccessoryRepositoryImpl(apiService, preferences)
    }

    @Provides
    @Singleton
    fun provideRoomDataBase(app: Application): AccessoriesDatabase {
        return Room.databaseBuilder(
            app,
            AccessoriesDatabase::class.java,
            Constants.SECOND_DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(db: AccessoriesDatabase): AccessoriesRoomRepository {
        return AccessoriesRoomRepositoryImpl(db.accessoriesDao)
    }
}