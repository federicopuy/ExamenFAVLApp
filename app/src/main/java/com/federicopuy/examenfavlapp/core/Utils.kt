package com.federicopuy.examenfavlapp.core

import android.content.Context
import com.federicopuy.examenfavlapp.R
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String {
    try {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (exception: IOException) {
        throw IOException("Failed to get JSON object from string")
    }
}

/**
 * Workaround to obtain Drawables from string identifiers. The behavior of retrieving a Drawable from
 * with its id is not available in Compose yet, so we use this workaround, where we match each it to
 * its corresponding drawable.
 */
fun getResourceFromString(path: String): Int {
    return when (path) {
        "circuit" -> R.drawable.circuit
        "approach" -> R.drawable.approach
        else -> throw IllegalArgumentException("Invalid resource")
    }
}