package com.example.quizzywizzy

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get all ImageView elements
        val imageView1 = findViewById<ImageView>(R.id.imageView)
        val imageView2 = findViewById<ImageView>(R.id.imageView2)
        val imageView3 = findViewById<ImageView>(R.id.imageView3)
        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        val imageView5 = findViewById<ImageView>(R.id.imageView5)

        // Add animations to each ImageView
        animateTranslationX(imageView1)
        animateTranslationX(imageView2)
        animateTranslationX(imageView3)
        animateTranslationX(imageView4)
        animateTranslationX(imageView5)

        // Reference to RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.categoriesRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Define categories
        val categories = listOf(
            Category("Math", R.drawable.math, Color.parseColor("#FFEB3B"), Color.parseColor("#8BC34A"), R.drawable.mathsbackground), // Yellow background, Black text
            Category("Alphabet", R.drawable.alphabet, Color.parseColor("#8BC34A"), Color.parseColor("#E91E63"), R.drawable.alphabetsbackground), // Green background, White text
            Category("Word", R.drawable.words, Color.parseColor("#03A9F4"), Color.parseColor("#FFEB3B"), R.drawable.wordsbackground), // Blue background, White text
            Category("Trivia", R.drawable.trivia, Color.parseColor("#E91E63"), Color.parseColor("#FFFFFF"), R.drawable.triviabackground) // Pink background, White text
        )


        // Set up adapter
        recyclerView.adapter = CategoriesAdapter(categories) { category ->
            // Handle category click and navigate to QuizActivity
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("CATEGORY", category.name) // Pass selected category to QuizActivity
            intent.putExtra("BACKGROUND", category.background) // Pass selected category to QuizActivity
            startActivity(intent)
        }
    }

    private fun animateTranslationX(view: View) {

        val screenWidth = resources.displayMetrics.widthPixels

        val animator = ObjectAnimator.ofFloat(
            view,
            "translationX",
            -screenWidth.toFloat(),
            screenWidth.toFloat(),
            -screenWidth.toFloat()
        )
        animator.duration = 20000 // Animation duration in milliseconds
        animator.repeatCount = ObjectAnimator.INFINITE // Repeat indefinitely
        animator.interpolator = AccelerateDecelerateInterpolator() // Smooth animation
        animator.start()
    }

}


