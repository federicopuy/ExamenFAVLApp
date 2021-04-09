package com.federicopuy.examenfavlapp.presentation.flows.mainmenu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.federicopuy.examenfavlapp.data.model.Category
import com.federicopuy.examenfavlapp.data.repositories.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: QuestionRepository) :
    ViewModel() {

    val categories: LiveData<List<Category>> = repository.obtainCategories().asLiveData()
    var clickedCategoryId : Long by mutableStateOf(-1)

    fun initViewModel(shouldUpdateDB: Boolean) {
        if (shouldUpdateDB) {
            viewModelScope.launch {
                repository.updateDB()
            }
        }
    }

    fun onCategoryClicked(category: Category) {
        clickedCategoryId =  category.id
    }

    fun onFabClicked() {
        // The number 0 corresponds to the "Favorites" category
        clickedCategoryId = 0
    }

    fun resetClickedCategory() {
        clickedCategoryId = -1
    }
}