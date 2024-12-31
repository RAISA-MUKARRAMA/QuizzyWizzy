package com.example.quizzywizzy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> get() = _questions

    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    private val _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int> get() = _currentIndex

    fun loadQuestions(category: String) {
        viewModelScope.launch {
            try {
                _questions.value = repository.fetchQuestions(category,10)
            } catch (e: Exception) {
                _questions.value = emptyList()
            }
        }
    }

    fun incrementScore() {
        _score.value = (_score.value ?: 0) + 1
    }

    fun nextQuestion() {
        _currentIndex.value = (_currentIndex.value ?: 0) + 1
    }
}