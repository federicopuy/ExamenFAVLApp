package com.federicopuy.examenfavlapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.federicopuy.examenfavlapp.data.local.entities.AnswerEntity
import com.federicopuy.examenfavlapp.data.local.entities.QuestionEntity
import com.federicopuy.examenfavlapp.data.local.entities.QuestionWithAnswers

@Dao
abstract class QuestionDao {

    @Transaction
    @Query("SELECT * FROM questions")
    abstract fun getQuestionsWithAnswers(): List<QuestionWithAnswers>

    @Transaction
    @Query("SELECT * FROM questions WHERE favorite = 1")
    abstract fun getFavoriteQuestionsWithAnswers(): List<QuestionWithAnswers>

    @Transaction
    @Query("SELECT * FROM questions WHERE categoryId = :categoryId")
    abstract fun getQuestionsForCategory(categoryId: Long): List<QuestionWithAnswers>

    @Query("SELECT * FROM questions WHERE id = :questionId")
    abstract fun getQuestion(questionId: Long): List<QuestionEntity>

    @Query("UPDATE questions SET favorite = :favorite WHERE id =:questionId")
    abstract fun updateQuestion(favorite: Boolean, questionId: Long)

    @Insert
    abstract fun insertQuestion(question: QuestionEntity)

    @Insert
    abstract fun insertAnswers(answers: List<AnswerEntity>)

    @Query("DELETE FROM questions")
    abstract fun nukeQuestions()


}