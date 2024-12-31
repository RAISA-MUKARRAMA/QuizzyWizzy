package com.example.quizzywizzy

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class QuizRepository {
    private val database = FirebaseDatabase.getInstance()

    suspend fun fetchQuestions(category: String, count: Int): List<Question> {
        val quizRef = database.getReference("quiz_categories/$category")
        val snapshot = quizRef.get().await()
        val allquestions = snapshot.children.mapNotNull { it.getValue(Question::class.java) }

        return allquestions.shuffled().take(count)
    }
}