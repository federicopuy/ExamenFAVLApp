package com.federicopuy.examenfavlapp.data.local.daos

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AnswerDao {

    @Query("DELETE FROM answers")
    fun nukeAnswers()
}