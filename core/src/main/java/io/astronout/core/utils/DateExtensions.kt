package io.astronout.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.convertDateTo(desireFormat: ConverterDate, originalFormat: ConverterDate = ConverterDate.SQL_DATE): String {
    return if (this.isNotEmpty()) {
        val dateFormat = SimpleDateFormat(originalFormat.formatter, Locale.getDefault())
        val date = dateFormat.parse(this) ?: Date()
        dateFormat.applyPattern(desireFormat.formatter)
        dateFormat.format(date)
    } else {
        "-"
    }
}

