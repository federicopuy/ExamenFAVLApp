package com.federicopuy.examenfavlapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.federicopuy.examenfavlapp.data.local.entities.*
import com.federicopuy.examenfavlapp.data.model.Category
import com.federicopuy.examenfavlapp.data.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    fun insertCategory(category: Category, questions: List<Question>) {
        insertCategory(category.asCategoryEntity())

        for (question in questions) {
            insertQuestion(category, question, question.answers.asListOfAnswerEntities())
        }
    }

    fun insertQuestion(category: Category, question: Question, answers: List<AnswerEntity>) {
        for (answer in answers) {
            answer.questionId = question.id
        }
        val questionEntity = question.asQuestionEntity()
        questionEntity.categoryId = category.id

        insertQuestion(questionEntity)
        insertAnswers(answers)
    }

    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Insert
    fun insertCategory(category: CategoryEntity)

    @Insert
    fun insertQuestion(question: QuestionEntity)

    @Insert
    fun insertAnswers(answers: List<AnswerEntity>)

    @Query("DELETE FROM categories")
    fun nukeCategories()

}