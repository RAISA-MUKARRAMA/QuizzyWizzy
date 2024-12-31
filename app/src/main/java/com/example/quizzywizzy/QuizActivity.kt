package com.example.quizzywizzy

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class QuizActivity : AppCompatActivity() {

    private val viewModel: QuizViewModel by viewModels {
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return QuizViewModel(QuizRepository()) as T
            }
        }
    }

    private lateinit var selectedCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Get the selected category
        selectedCategory = intent.getStringExtra("CATEGORY") ?: "math"
        selectedCategory = selectedCategory.lowercase()
        Log.d("selectedCategory", "onCreate: ${selectedCategory.lowercase()}")

        val selectedCategoryBackground = intent.getIntExtra("BACKGROUND", R.drawable.mathsbackground)
        findViewById<ConstraintLayout>(R.id.quizActivityScreen).setBackgroundResource(selectedCategoryBackground)

        viewModel.questions.observe(this, Observer { questions ->
            if (questions.isNotEmpty()) {
                loadQuestion(viewModel.currentIndex.value ?: 0, questions)
            } else {
                Toast.makeText(this, "Failed to load questions", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.loadQuestions(selectedCategory)

        val imageview7 = findViewById<ImageView>(R.id.imageView7)
        val imageview8 = findViewById<ImageView>(R.id.imageView8)

        animateRotateTranslate(imageview7)
        animateRotateTranslate(imageview8)
    }

    private fun loadQuestion(index: Int, questions: List<Question>) {
        if (index < questions.size) {
            val questionNumber = findViewById<TextView>(R.id.questionNumber)
            val categoryName: TextView = findViewById<TextView>(R.id.categoryName)

            categoryName.text = selectedCategory.uppercase()
            questionNumber.text = "Question: $index/${questions.size}    Correct: ${viewModel.score.value}"

            val currentQuestion = questions[index]

            // Display the question and options
            findViewById<TextView>(R.id.questionText).text = currentQuestion.question
            findViewById<Button>(R.id.option1Button).text = currentQuestion.options[0]
            findViewById<Button>(R.id.option2Button).text = currentQuestion.options[1]
            findViewById<Button>(R.id.option3Button).text = currentQuestion.options[2]
            findViewById<Button>(R.id.option4Button).text = currentQuestion.options[3]

            // Set click listeners for the options
            findViewById<Button>(R.id.option1Button).setOnClickListener { checkAnswer( 0) }
            findViewById<Button>(R.id.option2Button).setOnClickListener { checkAnswer( 1) }
            findViewById<Button>(R.id.option3Button).setOnClickListener { checkAnswer( 2) }
            findViewById<Button>(R.id.option4Button).setOnClickListener { checkAnswer( 3) }
        } else {
            // No more questions, show the result
            showResult()
        }
    }

    private fun checkAnswer(selectedOptionIndex: Int) {

        val currentQuestion = viewModel.questions.value?.get(viewModel.currentIndex.value ?: 0)

        val resultPopUp = findViewById<ImageView>(R.id.resultPopup)

        if (currentQuestion != null && currentQuestion.options[selectedOptionIndex] == currentQuestion.answer) {
            viewModel.incrementScore()
//            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            resultPopUp.setImageResource(R.drawable.greatjob)
        } else {
            //Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show()
            resultPopUp.setImageResource(R.drawable.wronganser)
        }

        resultPopUp.visibility = View.VISIBLE

        // Hide the pop-up after 2 seconds and load the next question
        resultPopUp.postDelayed({
            resultPopUp.visibility = View.GONE

            // Load the next question
            viewModel.nextQuestion()
            loadQuestion(viewModel.currentIndex.value ?: 0, viewModel.questions.value ?: emptyList())
        }, 700)
    }

    private fun showResult() {
//        Toast.makeText(
//            this,
//            "Quiz Over! Your Score: $score/${questionsList.size}",
//            Toast.LENGTH_LONG
//        ).show()
        val intent1 = Intent(this, ResultActivity::class.java)
        intent1.putExtra("score", "${viewModel.score.value}")
        startActivity(intent1)
        finish()
    }

    private fun animateRotateTranslate(view: View) {

        val screenHeight = resources.displayMetrics.heightPixels

        val animatorT = ObjectAnimator.ofFloat(
            view,
            "translationY",
            screenHeight.toFloat(),
            -screenHeight.toFloat(),
        )
        animatorT.duration = 20000 // Animation duration in milliseconds
        animatorT.repeatCount = ObjectAnimator.INFINITE // Repeat indefinitely
        animatorT.interpolator = AccelerateDecelerateInterpolator() // Smooth animation

        val animatorS = ObjectAnimator.ofFloat(
            view,
            "rotation",
            -15f,
            15f,
            -15f
        )
        animatorS.duration = 5000 // Animation duration in milliseconds
        animatorS.repeatCount = ObjectAnimator.INFINITE // Repeat indefinitely
        animatorS.interpolator = AccelerateDecelerateInterpolator() // Smooth animation

        animatorT.start()
        animatorS.start()
    }
}



