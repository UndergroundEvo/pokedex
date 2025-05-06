package com.sibers.domain.entity

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val type: List<String>,
    val stats: Map<String, Int>,
    val image: String,
    val isHighlighted: Boolean = false,
)
