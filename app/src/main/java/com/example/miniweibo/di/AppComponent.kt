package com.example.miniweibo.di

import android.app.Application
import com.example.miniweibo.MyApplication
import com.example.miniweibo.util.EmotionUtil
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        AppModel::class,
        ActivityBuildersModel::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(myApplication: MyApplication)

    fun inject(emotionUtil: EmotionUtil)
}