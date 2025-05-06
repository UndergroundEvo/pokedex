package com.sibers.common

import java.net.URL

object Utills {
    fun extractIdFromUrl(url: String): Int? {
        return try {
            URL(url).path.trim('/').substringAfterLast('/').toIntOrNull()
        } catch (e: Exception) {
            null
        }
    }
}