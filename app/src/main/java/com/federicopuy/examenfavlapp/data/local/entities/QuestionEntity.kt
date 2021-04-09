package com.federicopuy.examenfavlapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.federicopuy.examenfavlapp.data.model.Question

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey val id: Long,
    val description: String,
    val image: String?,
    val favorite: Boolean = false,
    var categoryId : Long = 0
)

fun Question.asQuestionEntity(): QuestionEntity {
    return QuestionEntity(id, title, image, favorite)
}

fun List<Question>.asListOfQuestionEntities(): List<QuestionEntity> {
    return map { it.asQuestionEntity() }
}
