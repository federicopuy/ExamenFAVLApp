package com.federicopuy.examenfavlapp.data.repositories

import android.content.Context
import com.federicopuy.examenfavlapp.core.JSON_FILE_NAME
import com.federicopuy.examenfavlapp.core.getJsonDataFromAsset
import com.federicopuy.examenfavlapp.data.local.AppDatabase
import com.federicopuy.examenfavlapp.data.local.entities.asListOfCategories
import com.federicopuy.examenfavlapp.data.local.entities.asListOfQuestions
import com.federicopuy.examenfavlapp.data.model.Category
import com.federicopuy.examenfavlapp.data.model.LicenseType
import com.federicopuy.examenfavlapp.data.model.Question
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@FlowPreview
@ExperimentalCoroutinesApi
class QuestionRepository(private val appContext: Context, val database: AppDatabase) {

    suspend fun updateDB() = withContext(Dispatchers.IO) {
        database.categoryDao().nukeCategories()
        database.questionDao().nukeQuestions()
        database.answerDao().nukeAnswers()

        insertQuestionsToDB()
    }

    private fun insertQuestionsToDB() {
        val str = getJsonDataFromAsset(appContext, JSON_FILE_NAME)
        val gson = Gson()
        val licenseType = object : TypeToken<List<LicenseType>>() {}.type

        val license: List<LicenseType> = gson.fromJson(str, licenseType)

        for (category in license[0].categories) {
            database.categoryDao().insertCategory(category, category.questions)
        }
    }

    fun obtainCategories(): Flow<List<Category>> =
        database.categoryDao().getCategories().map { it.asListOfCategories() }

    private suspend fun obtainFavoriteQuestions(): Flow<List<Question>> = flow {
        val questionsWithFavorites = database.questionDao().getFavoriteQuestionsWithAnswers()
        emit(questionsWithFavorites.asListOfQuestions())
    }.flowOn(Dispatchers.IO)

    private suspend fun obtainQuestionsForCategory(id: Long): Flow<List<Question>> = flow {
        val questionsWithFavorites = database.questionDao().getQuestionsForCategory(id)
        emit(questionsWithFavorites.asListOfQuestions())
    }.flowOn(Dispatchers.IO)

    suspend fun obtainQuestionsList(): Flow<List<Question>> = flow {
        val questionsWithFavorites = database.questionDao().getQuestionsWithAnswers()
        emit(questionsWithFavorites.asListOfQuestions())
    }.flowOn(Dispatchers.IO)

    suspend fun getQuestions(type: Long): Flow<List<Question>> {
        if (type == 0L) {
            return obtainFavoriteQuestions()
        }
        return obtainQuestionsForCategory(type)
    }

    suspend fun saveQuestionInFavorites(id: Long, favorite: Boolean) =
        withContext(Dispatchers.IO) {
            database.questionDao().updateQuestion(favorite, id)
        }
}