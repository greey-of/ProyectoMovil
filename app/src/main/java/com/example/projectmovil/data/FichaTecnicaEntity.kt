package com.example.examen.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "//nomvbre de la trabla")
data class FichaTecnicaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//agregar como sea necesario
)
