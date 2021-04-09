package com.federicopuy.examenfavlapp.core.di

import android.content.Context
import androidx.room.Room
import com.federicopuy.examenfavlapp.data.local.AppDatabase
import com.federicopuy.examenfavlapp.data.local.daos.AnswerDao
import com.federicopuy.examenfavlapp.data.local.daos.CategoryDao
import com.federicopuy.examenfavlapp.data.local.daos.LicenseTypeDao
import com.federicopuy.examenfavlapp.data.local.daos.QuestionDao
import com.federicopuy.examenfavlapp.data.repositories.QuestionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    private const val DATABASE_NAME = "FAVL_EXAM_DB"

    @Provides
    @Singleton
    fun provideQuestionsRepository(
        @ApplicationContext appContext: Context,
        database: AppDatabase
    ): QuestionRepository {
        return QuestionRepository(appContext, database)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideQuestionDao(database: AppDatabase): QuestionDao {
        return database.questionDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun provideLicenseTypeDao(database: AppDatabase): LicenseTypeDao {
        return database.licenseTypeDao()
    }

    @Provides
    @Singleton
    fun provideAnswerDao(database: AppDatabase): AnswerDao {
        return database.answerDao()
    }

}