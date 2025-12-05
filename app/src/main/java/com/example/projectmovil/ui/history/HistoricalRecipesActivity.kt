package com.example.projectmovil.ui.history

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmovil.R
import com.example.projectmovil.data.FilterOptions
import com.example.projectmovil.data.HistoricalRecipe
import com.example.projectmovil.data.HistoricalRecipeProvider
import com.google.android.material.textfield.TextInputLayout

// Quitamos la referencia a ActivityHistoricalRecipesBinding
class HistoricalRecipesActivity : AppCompatActivity() {

    private val originalRecipes = HistoricalRecipeProvider.historicalRecipes
    private var currentRecipes: List<HistoricalRecipe> = originalRecipes

    // Declaración de Views para usarlos con findViewById
    private lateinit var recipesContainer: LinearLayout
    private lateinit var btnBack: ImageView
    private lateinit var btnClearFilters: ImageButton

    // Referencias a los AutoCompleteTextView para poder limpiarlos
    private lateinit var dropdownType: AutoCompleteTextView
    private lateinit var dropdownRating: AutoCompleteTextView
    private lateinit var dropdownIngredient: AutoCompleteTextView
    private lateinit var dropdownCalories: AutoCompleteTextView

    private data class FilterState(
        var selectedType: String? = null,
        var selectedMinRating: Float? = null,
        var selectedIngredient: String? = null,
        var selectedCalories: String? = null
    )
    private val filterState = FilterState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historical_recipes) // Error 'setContentView' resuelto

        // Inicialización de Views usando findViewById (reemplazo de ViewBinding)
        recipesContainer = findViewById(R.id.recipes_container)
        btnBack = findViewById(R.id.btn_back)
        btnClearFilters = findViewById(R.id.btn_clear_filters)

        dropdownType = findViewById(R.id.filter_type_dropdown)
        dropdownRating = findViewById(R.id.filter_rating_dropdown)
        dropdownIngredient = findViewById(R.id.filter_ingredient_dropdown)
        dropdownCalories = findViewById(R.id.filter_calories_dropdown)


        // --- 1. CONFIGURACIÓN INICIAL DE FILTROS Y UI ---
        val filterOptions = HistoricalRecipeProvider.getFilterOptions()
        setupFilterDropdowns(filterOptions)

        // --- 2. LISTENERS ---
        btnBack.setOnClickListener {
            finish()
        }

        btnClearFilters.setOnClickListener {
            clearFiltersUIAndLogic()
        }

        updateRecipesUI(originalRecipes)
    }

    private fun setupFilterDropdowns(filterOptions: FilterOptions) {

        // Función auxiliar
        fun setupDropdown(dropdown: AutoCompleteTextView, options: List<String>, updateState: (String?) -> Unit) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                options
            )
            dropdown.setAdapter(adapter)

            dropdown.setOnItemClickListener { parent, _, position, _ ->
                val selectedValue = parent.getItemAtPosition(position).toString()
                updateState(selectedValue)
                applyFilters()
            }

            // Listener para detectar que se borró manualmente la selección
            dropdown.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && dropdown.text.isNullOrEmpty()) {
                    updateState(null)
                    applyFilters()
                }
            }
        }

        // Aplicar la configuración a cada filtro
        setupDropdown(dropdownType, filterOptions.types) { filterState.selectedType = it }

        // Rating: El estado se actualiza a Float (el rating mínimo)
        setupDropdown(dropdownRating, filterOptions.ratings) { selectedRatingString ->
            filterState.selectedMinRating = selectedRatingString?.let {
                // Creamos una receta dummy solo para acceder a la propiedad numericRating
                // Esto es necesario si no podemos modificar el constructor en HistoricalRecipe.kt
                // y necesitamos una instancia para usar la propiedad.
                HistoricalRecipe(
                    name = "", shortDescription = "", type = "",
                    rating = it, calories = "", history = "",
                    ingredients = emptyList(), preparation = emptyList()
                ).numericRating
            }
        }

        setupDropdown(dropdownIngredient, filterOptions.ingredients) { filterState.selectedIngredient = it }
        setupDropdown(dropdownCalories, filterOptions.calories) { filterState.selectedCalories = it }
    }

    private fun clearFiltersUIAndLogic() {
        filterState.selectedType = null
        filterState.selectedMinRating = null
        filterState.selectedIngredient = null
        filterState.selectedCalories = null

        dropdownType.setText("", false)
        dropdownRating.setText("", false)
        dropdownIngredient.setText("", false)
        dropdownCalories.setText("", false)

        applyFilters()
        Toast.makeText(this, "Filtros limpiados.", Toast.LENGTH_SHORT).show()
    }

    private fun applyFilters() {
        var filteredList: List<HistoricalRecipe> = originalRecipes

        filterState.selectedType?.let { type ->
            filteredList = filteredList.filter { it.type == type }
        }

        filterState.selectedMinRating?.let { minRating ->
            filteredList = filteredList.filter { recipe ->
                recipe.numericRating >= minRating // Usamos la propiedad de solo lectura corregida
            }
        }

        filterState.selectedIngredient?.let { ingredient ->
            filteredList = filteredList.filter { recipe ->
                recipe.ingredients.any { rawIngredient ->
                    rawIngredient.contains(ingredient, ignoreCase = true)
                }
            }
        }

        filterState.selectedCalories?.let { calories ->
            filteredList = filteredList.filter { it.calories == calories }
        }

        currentRecipes = filteredList
        updateRecipesUI(currentRecipes)
    }

    // Funciones updateRecipesUI y setupRecipeCard, etc. se mantienen igual, usando las
    // variables inicializadas con findViewById (ej: recipesContainer)

    private fun updateRecipesUI(recipes: List<HistoricalRecipe>) {
        recipesContainer.removeAllViews()

        if (recipes.isEmpty()) {
            val noResults = TextView(this).apply {
                text = "No se encontraron recetas con los filtros seleccionados."
                setPadding(32, 32, 32, 32)
                gravity = android.view.Gravity.CENTER
                textSize = 18f
                setTextColor(resources.getColor(R.color.text_secondary, theme))
            }
            recipesContainer.addView(noResults)
        } else {
            recipes.forEach { recipe ->
                val recipeCard = layoutInflater.inflate(R.layout.item_historical_recipe, recipesContainer, false)
                setupRecipeCard(recipeCard, recipe)
                recipesContainer.addView(recipeCard)
            }
        }
    }

    // ... (El resto de tus funciones como setupRecipeCard y showReviewDialog van aquí) ...
    // Asegúrate de que usan findViewById si es necesario.

    private fun setupRecipeCard(cardView: View, recipe: HistoricalRecipe) {
        // ... (código del setupRecipeCard) ...
        val tvTitle = cardView.findViewById<TextView>(R.id.tv_recipe_title)
        val tvRating = cardView.findViewById<TextView>(R.id.tv_recipe_rating)
        // ... (el resto de vistas) ...

        tvTitle.text = recipe.name
        tvRating.text = recipe.rating

        val btnShowMore = cardView.findViewById<Button>(R.id.btn_show_more)
        val expandedContent = cardView.findViewById<LinearLayout>(R.id.expanded_content)

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

        val btnAddReview = cardView.findViewById<Button>(R.id.btn_add_review)
        btnAddReview.setOnClickListener {
            showReviewDialog(recipe.name)
        }
    }

    private fun showReviewDialog(recipeName: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_review, null)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.rating_bar)

        AlertDialog.Builder(this)
            .setTitle("Reseña: $recipeName")
            .setView(dialogView)
            .setPositiveButton("Enviar") { _, _ ->
                val rating = ratingBar.rating
                Toast.makeText(this, "Reseña enviada: $rating estrellas para '$recipeName'", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}