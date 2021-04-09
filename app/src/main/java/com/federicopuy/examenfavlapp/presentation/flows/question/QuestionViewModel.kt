package com.federicopuy.examenfavlapp.presentation.flows.question

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.federicopuy.examenfavlapp.data.model.Answer
import com.federicopuy.examenfavlapp.data.model.Question
import com.federicopuy.examenfavlapp.data.repositories.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository: QuestionRepository) :
    ViewModel() {

    val questions = mutableListOf<Question>()
    var isAnswered: Boolean by mutableStateOf(false)
    var noMoreQuestions: Boolean by mutableStateOf(false)
    var currentQuestion: Question? by mutableStateOf(null)
    var isFavorite: Boolean by mutableStateOf(false)

    fun initViewModel(extra: Long) {
        viewModelScope.launch {
            repository.getQuestions(extra).collect {
                questions.addAll(it)
                getNextQuestion()
            }
        }
    }

    fun getNextQuestion() {
        isAnswered = false
        if (questions.isEmpty()) {
            noMoreQuestions = true
            return
        }
        val question = questions.removeAt(0)
        currentQuestion = question
        isFavorite = question.favorite
    }

    fun onAnswerClicked(answer: Answer) {
        val question = requireNotNull(currentQuestion)
        if (isAnswered) {
            // Do nothing, user should not be clicking answers of an already answered question
            return
        }
        // TODO make this more efficient
        question.answers.toMutableList().also {
            it.remove(answer)
            val newAns = answer
            newAns.pressed = answer.pressed.not()
            it.add(newAns)
        }
        currentQuestion = null
        currentQuestion = question
    }

    fun onPrimaryButtonClicked() {
        if (isAnswered) {
            getNextQuestion()
            return
        }
        calculateScore()
    }

    fun calculateScore() {
        isAnswered = true
    }

    fun onFavoriteClicked() {
        val question = currentQuestion ?: return
        val newFavoriteStatus = isFavorite.not()
        viewModelScope.launch {
            repository.saveQuestionInFavorites(question.id, newFavoriteStatus)
            isFavorite = newFavoriteStatus
        }
    }
}
