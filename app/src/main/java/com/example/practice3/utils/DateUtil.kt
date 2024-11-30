package com.example.practice3.utils

import org.threeten.bp.LocalTime

fun tryParse(date: String): LocalTime? {
    return try {
        LocalTime.parse(date)
    } catch (e: Exception) {
        null
    }
}