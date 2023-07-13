package com.example.quizzer.util

import java.util.*

fun String.capitalizeFirstWord() =
    this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
