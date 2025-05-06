package com.sibers.database.converters

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

object PokemonDbConverters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromTypeList(typeList: List<String>): String {
        return json.encodeToString(typeList)
    }

    @TypeConverter
    fun toTypeList(typeListString: String): List<String> {
        return json.decodeFromString(typeListString)
    }

    @TypeConverter
    fun fromStatsMap(statsMap: Map<String, Int>): String {
        return json.encodeToString(statsMap)
    }

    @TypeConverter
    fun toStatsMap(statsMapString: String): Map<String, Int> {
        return json.decodeFromString(statsMapString)
    }
}