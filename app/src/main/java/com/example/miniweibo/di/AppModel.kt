package com.example.miniweibo.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.constants.PlatformParameters
import com.example.miniweibo.data.db.AccessTokenDao
import com.example.miniweibo.data.db.EmotionDao
import com.example.miniweibo.data.db.MiniWeiBoDb
import com.example.miniweibo.data.db.UserInfoDao
import com.example.miniweibo.util.LiveDataCallAdapterFactory
import com.example.miniweibo.util.MyConverterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.UnsupportedEncodingException
import javax.inject.Singleton

@Module(includes = [ViewModelModel::class])
class AppModel {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor {
            try {
                Log.e("OkHttp------", it)
            } catch (e: UnsupportedEncodingException) {
                Log.e("OkHttp------", it)
            }
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient =
            OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
        return okHttpClient
    }

    @Singleton
    @Provides
    fun provideMoShi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): WeiBoService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(PlatformParameters.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
//            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())

//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(WeiBoService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(application: Application): MiniWeiBoDb {
        return MiniWeiBoDb.getInstance(application)
    }

    @Singleton
    @Provides
    fun provideAccessTokenDao(db: MiniWeiBoDb): AccessTokenDao {
        return db.accessTokenDao()
    }

    @Singleton
    @Provides
    fun provideEmotionDao(db: MiniWeiBoDb): EmotionDao {
        return db.emotionsDao()
    }

    @Singleton
    @Provides
    fun provideUserInfoDao(db: MiniWeiBoDb): UserInfoDao {
        return db.userInfoDao()
    }

}