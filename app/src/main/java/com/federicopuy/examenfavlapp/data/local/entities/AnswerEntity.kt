package com.federicopuy.examenfavlapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.federicopuy.examenfavlapp.data.model.Answer

@Entity(tableName = "answers")
data class AnswerEntity(
    @PrimaryKey (autoGenerate = true) var id: Long = 0,
    val title: String,
    val score: Int,
    var questionId: Long
)

fun Answer.asAnswerEntity(): AnswerEntity {
    return AnswerEntity(title = title, score = score, questionId = 0)
}

fun List<Answer>.asListOfAnswerEntities(): List<AnswerEntity> {
    return map { it.asAnswerEntity() }
}

fun AnswerEntity.asAnswer(): Answer {
    return Answer(id, title, score, false)
}

fun List<AnswerEntity>.asListOfAnswers(): List<Answer> {
    return map { it.asAnswer() }
}


