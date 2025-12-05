package com.example.projectmovil.data

// El constructor de la data class debe coincidir exactamente con el Provider
data class HistoricalRecipe(
    val name: String,
    val shortDescription: String,
    val type: String,
    val rating: String,
    val calories: String,
    val history: String,
    val ingredients: List<String>,
    val preparation: List<String>
) {
    // 1. PROPIEDAD: Permite obtener el rating como número (Float) de forma segura
    val numericRating: Float
        get() = try {
            rating.substringAfter("⭐ ").toFloat()
        } catch (e: Exception) {
            0.0f
        }

    // 2. PROPIEDAD: Permite obtener las calorías como número (Int) de forma segura
    val numericCalories: Int
        get() = try {
            // Encuentra el primer número en el string (ej: "280 kcal..." -> 280)
            Regex("\\d+").find(calories)?.value?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
}