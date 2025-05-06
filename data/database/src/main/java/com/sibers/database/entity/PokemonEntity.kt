package com.sibers.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sibers.common.Const

@Entity(tableName = Const.POKEMON_TABLE)
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val type: List<String>,
    val stats: Map<String, Int>,
    val image: String
)