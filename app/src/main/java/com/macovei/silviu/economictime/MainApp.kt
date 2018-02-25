package com.macovei.silviu.economictime

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.macovei.silviu.economictime.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by silviumacovei on 2/20/18.
 */

class MainApp : MultiDexApplication(), HasActivityInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

}