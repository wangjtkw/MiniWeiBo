package com.example.miniweibo.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.constants.PlatformParameters
import com.example.miniweibo.data.db.AccessTokenDao
import com.example.miniweibo.data.db.MiniWeiBoDb
import com.example.miniweibo.data.db.UserInfoDao
import com.example.miniweibo.util.LiveDataCallAdapterFactory
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
    fun provideRetrofit(okHttpClient: OkHttpClient): WeiBoService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(PlatformParameters.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(WeiBoService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(application: Application): MiniWeiBoDb {
        return Room.databaseBuilder(application, MiniWeiBoDb::class.java, "miniweibo.db")
            .fallbackToDestructiveMigration()//数据库更新时删除数据重新创建
            .build()
    }

    @Singleton
    @Provides
    fun provideAccessTokenDao(db: MiniWeiBoDb): AccessTokenDao {
        return db.accessTokenDao()
    }

    @Singleton
    @Provides
    fun provideUserInfoDao(db: MiniWeiBoDb): UserInfoDao {
        return db.userInfoDao()
    }

}