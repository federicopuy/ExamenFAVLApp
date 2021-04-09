package com.federicopuy.examenfavlapp.data.model

data class Question(
    val id: Long,
    val title: String,
    val image: String?,
    var favorite : Boolean = false,
    var answers: List<Answer>
)