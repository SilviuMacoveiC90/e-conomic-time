package com.macovei.silviu.economictime.di

import android.app.Application
import com.macovei.silviu.economictime.MainApp
import com.macovei.silviu.economictime.di.modules.ActivityBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@AppScope
@Component(modules =
[AndroidInjectionModule::class,
    AppModule::class,
    ActivityBuilderModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(mainApp: MainApp)
}

