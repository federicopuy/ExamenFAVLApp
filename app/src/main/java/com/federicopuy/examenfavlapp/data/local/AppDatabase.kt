package com.federicopuy.examenfavlapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.federicopuy.examenfavlapp.data.local.daos.AnswerDao
import com.federicopuy.examenfavlapp.data.local.daos.CategoryDao
import com.federicopuy.examenfavlapp.data.local.daos.LicenseTypeDao
import com.federicopuy.examenfavlapp.data.local.daos.QuestionDao
import com.federicopuy.examenfavlapp.data.local.entities.AnswerEntity
import com.federicopuy.examenfavlapp.data.local.entities.CategoryEntity
import com.federicopuy.examenfavlapp.data.local.entities.LicenseTypeEntity
import com.federicopuy.examenfavlapp.data.local.entities.QuestionEntity

@Database(
    entities = [CategoryEntity::class, LicenseTypeEntity::class, QuestionEntity::class, AnswerEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

    abstract fun licenseTypeDao(): LicenseTypeDao

    abstract fun categoryDao(): CategoryDao

    abstract fun answerDao(): AnswerDao

}