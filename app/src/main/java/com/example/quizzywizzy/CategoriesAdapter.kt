package com.example.quizzywizzy

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CategoriesAdapter(
    private val categories: List<Category>,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catagory, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, onClick)
    }

    override fun getItemCount() = categories.size

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon: ImageView = view.findViewById(R.id.categoryIcon)
        private val name: TextView = view.findViewById(R.id.categoryName)
        private val categoryFrame: LinearLayout = view.findViewById(R.id.categoryFrame)


        fun bind(category: Category, onClick: (Category) -> Unit) {
            icon.setImageResource(category.iconRes)
            name.text = category.name
            categoryFrame.setBackgroundColor(category.backgroundColor)
            name.setTextColor(category.textColor)
            itemView.setOnClickListener { onClick(category) }

        }
    }

}

