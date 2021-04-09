package com.federicopuy.examenfavlapp.core.di

import com.federicopuy.examenfavlapp.core.updateDBManager.DefaultUpdateDBManager
import com.federicopuy.examenfavlapp.core.updateDBManager.UpdateDBManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class ActivityRetainedModule {

    @Binds
    abstract fun bindUpdateDBManager(impl: DefaultUpdateDBManager): UpdateDBManager
}