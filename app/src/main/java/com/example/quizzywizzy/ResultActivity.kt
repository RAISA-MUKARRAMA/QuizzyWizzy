package com.example.quizzywizzy

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Handle padding for system bars
        val rootView = findViewById<View>(R.id.main)

        // Reference the ImageView elements
        val resultPopup1 = findViewById<ImageView>(R.id.resultPopup)
        val resultPopup2 = findViewById<ImageView>(R.id.resultPopup2)
        val resultPopup3 = findViewById<ImageView>(R.id.resultPopup3)
        val resultPopup4 = findViewById<ImageView>(R.id.resultPopup4)

        // Animate each ImageView
        fadeInView(resultPopup1, 0)
        fadeInView(resultPopup2, 300)
        fadeInView(resultPopup3, 600)
        fadeInView(resultPopup4, 900)

        val obtainedScore = intent.getStringExtra("score")

        findViewById<TextView>(R.id.score).text = "Your Score: $obtainedScore"

        findViewById<Button>(R.id.continueButton).setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun fadeInView(view: View, startDelay: Long) {
        val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f,0f) // Animate alpha from 0 to 1
        animator.duration = 900 // Duration for the animation
        animator.startDelay = startDelay // Start delay for staggered effect
        animator.interpolator = AccelerateDecelerateInterpolator() // Smooth animation
        animator.repeatCount =  ObjectAnimator.INFINITE
        animator.start()
    }
}

