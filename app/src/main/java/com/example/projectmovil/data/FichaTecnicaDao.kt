package com.example.examen.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FichaTecnicaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ficha: FichaTecnicaEntity)

    @Query("SELECT * FROM fichas_tecnicas ORDER BY id DESC")
    suspend fun getAll(): List<FichaTecnicaEntity>

    @Query("SELECT * FROM fichas_tecnicas WHERE id = :idFicha")
    suspend fun getById(idFicha: Int): FichaTecnicaEntity?
}
