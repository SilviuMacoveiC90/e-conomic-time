package com.macovei.silviu.economictime.di.modules

import com.macovei.silviu.economictime.di.FragmentScope
import com.macovei.silviu.economictime.ui.details.DetailsFragment
import com.macovei.silviu.economictime.ui.list.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuildersModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun contributeListFragment(): ListFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun contributeDetailsFragment(): DetailsFragment
}

