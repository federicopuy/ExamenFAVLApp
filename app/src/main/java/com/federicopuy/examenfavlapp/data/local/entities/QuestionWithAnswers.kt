package com.federicopuy.examenfavlapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.federicopuy.examenfavlapp.data.model.Question

data class QuestionWithAnswers(
    @Embedded val question: QuestionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val answers: List<AnswerEntity>
)

fun QuestionWithAnswers.asQuestion(): Question {
    return Question(question.id, question.description, question.image, question.favorite,answers.asListOfAnswers())
}

fun List<QuestionWithAnswers>.asListOfQuestions(): List<Question> {
    return map { it.asQuestion() }
}