package com.federicopuy.examenfavlapp.data.model

data class Answer(val id: Long, val title: String, val score: Int, var pressed: Boolean = false)