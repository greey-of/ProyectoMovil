package com.example.projectmovil.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projectmovil.data.entity.FichaTecnicaEntity
import com.example.projectmovil.data.entity.FichaTecnicaDao

@Database(entities = [FichaTecnicaEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fichaTecnicaDao(): FichaTecnicaDao
}