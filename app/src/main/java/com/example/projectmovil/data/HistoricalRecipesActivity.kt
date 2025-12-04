package com.example.projectmovil

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
// Importar los componentes de datos
import com.example.projectmovil.data.HistoricalRecipeProvider
import com.example.projectmovil.data.HistoricalRecipe
// Asegúrate de tener los layouts R.layout.activity_historical_recipes, etc.

class HistoricalRecipesActivity : AppCompatActivity() {

    // Obtiene la lista de recetas desde la capa de datos (Provider)
    private val historicalRecipes = HistoricalRecipeProvider.historicalRecipes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historical_recipes)

        val btnBack = findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }

        val recipesContainer = findViewById<LinearLayout>(R.id.recipes_container)

        // Itera sobre la lista de recetas para inflar y configurar las tarjetas
        historicalRecipes.forEach { recipe ->
            // Se asume que tienes un layout llamado item_historical_recipe.xml
            val recipeCard = layoutInflater.inflate(R.layout.item_historical_recipe, recipesContainer, false)
            setupRecipeCard(recipeCard, recipe)
            recipesContainer.addView(recipeCard)
        }
    }

    private fun setupRecipeCard(cardView: View, recipe: HistoricalRecipe) {
        val tvTitle = cardView.findViewById<TextView>(R.id.tv_recipe_title)
        val tvDescription = cardView.findViewById<TextView>(R.id.tv_recipe_description)
        val tvRating = cardView.findViewById<TextView>(R.id.tv_recipe_rating)
        val tvCalories = cardView.findViewById<TextView>(R.id.tv_recipe_calories)
        val tvHistory = cardView.findViewById<TextView>(R.id.tv_recipe_history)
        val tvIngredients = cardView.findViewById<TextView>(R.id.tv_recipe_ingredients)
        val tvPreparation = cardView.findViewById<TextView>(R.id.tv_recipe_preparation)
        val btnShowMore = cardView.findViewById<Button>(R.id.btn_show_more)
        val btnAddReview = cardView.findViewById<Button>(R.id.btn_add_review)
        val expandedContent = cardView.findViewById<LinearLayout>(R.id.expanded_content)

        tvTitle.text = recipe.name
        tvDescription.text = recipe.shortDescription
        tvRating.text = recipe.rating
        tvCalories.text = recipe.calories
        tvHistory.text = recipe.history

        // Formato para los ingredientes (con viñetas)
        val ingredientsText = recipe.ingredients.joinToString("\n") { "• $it" }
        tvIngredients.text = ingredientsText

        // Formato para los pasos (con números)
        val preparationText = recipe.preparation.mapIndexed { index, step ->
            "${index + 1}. $step"
        }.joinToString("\n\n")
        tvPreparation.text = preparationText

        // Lógica de expansión/colapso
        var isExpanded = false
        expandedContent.visibility = View.GONE

        btnShowMore.setOnClickListener {
            isExpanded = !isExpanded
            if (isExpanded) {
                expandedContent.visibility = View.VISIBLE
                btnShowMore.text = "Mostrar menos ▲"
            } else {
                expandedContent.visibility = View.GONE
                btnShowMore.text = "Mostrar más ▼"
            }
        }

        // Mostrar diálogo de reseña
        btnAddReview.setOnClickListener {
            showReviewDialog(recipe.name)
        }
    }

    private fun showReviewDialog(recipeName: String) {
        // Se asume que tienes un layout llamado dialog_add_review.xml
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_review, null)
        val etReview = dialogView.findViewById<EditText>(R.id.et_review)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.rating_bar)

        AlertDialog.Builder(this)
            .setTitle("Reseña: $recipeName")
            .setView(dialogView)
            .setPositiveButton("Enviar") { _, _ ->
                val review = etReview.text.toString()
                val rating = ratingBar.rating
                Toast.makeText(
                    this,
                    "Reseña enviada: $rating estrellas",
                    Toast.LENGTH_SHORT
                ).show()
                // TODO: Aquí iría la lógica para guardar la reseña en la base de datos (Room)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}