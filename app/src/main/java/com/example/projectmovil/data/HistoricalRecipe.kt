package com.example.projectmovil.data

// Define la estructura de una receta histórica (MODELO DE DATOS)
data class HistoricalRecipe(
    val name: String,
    val shortDescription: String,
    val rating: String,
    val calories: String,
    val history: String,
    val ingredients: List<String>,
    val preparation: List<String>
)

// Objeto Singleton que proporciona la lista de recetas estáticas (PROVEEDOR)
object HistoricalRecipeProvider {

    val historicalRecipes = listOf(
        HistoricalRecipe(
            "Garum Romano",
            "Salsa de pescado fermentado usada en la antigua Roma",
            "⭐ 4.2",
            "150 kcal por porción",
            "El garum era una salsa de pescado fermentado extremadamente popular...",
            listOf("1 kg de pescado pequeño (anchoas, sardinas)", "300g de sal marina", "..."),
            listOf("Mezclar el pescado con la sal en una proporción 3:1", "Colocar en un recipiente...")
        ),
        HistoricalRecipe(
            "Pottage Medieval",
            "Estofado espeso del medioevo europeo",
            "⭐ 4.5",
            "320 kcal por porción",
            "El pottage era el plato básico de la dieta medieval europea, consumido por todas las clases sociales...",
            listOf("2 tazas de cebada o avena", "3 cebollas grandes", "..."),
            listOf("Remojar la cebada en agua durante la noche", "Picar todas las verduras...")
        ),
        HistoricalRecipe(
            "Pulque Azteca",
            "Bebida fermentada prehispánica del maguey",
            "⭐ 4.0",
            "110 kcal por vaso",
            "El pulque, conocido como 'octli' en náhuatl, era considerado una bebida sagrada por los aztecas...",
            listOf("Aguamiel fresco de maguey", "Semilla de pulque (madre)", "..."),
            listOf("Raspar el corazón del maguey maduro (8-10 años)", "Extraer el aguamiel...")
        ),
        HistoricalRecipe(
            "Kumis Mongol",
            "Leche de yegua fermentada de las estepas",
            "⭐ 3.8",
            "90 kcal por vaso",
            "El kumis era la bebida fundamental de los pueblos nómadas de las estepas asiáticas...",
            listOf("5 litros de leche de yegua fresca", "Cultivo iniciador de kumis anterior", "..."),
            listOf("Ordeñar las yeguas en la mañana", "Verter la leche en el odre de cuero...")
        ),
        HistoricalRecipe(
            "Posca del Legionario",
            "Bebida ácida de los soldados romanos",
            "⭐ 4.3",
            "45 kcal por vaso",
            "La posca era la bebida oficial de los legionarios romanos, una mezcla de vinagre agrio con agua y hierbas...",
            listOf("1 parte de vinagre de vino", "3-4 partes de agua", "..."),
            listOf("Mezclar el vinagre con agua en proporción 1:3", "Añadir una cucharada de miel...")
        )
    )
}