package com.sibers.pokemon_list.presentation.filter

import kotlinx.serialization.Serializable

@Serializable
data class FilterCriteria(
    val attack: Boolean = false,
    val defense: Boolean = false,
    val hp: Boolean = false
) {
    val isFilterActive: Boolean
        get() = attack || defense || hp
}