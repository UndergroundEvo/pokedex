package com.sibers.domain.repository

interface ConnectivityChecker {
    fun isOnline(): Boolean
}