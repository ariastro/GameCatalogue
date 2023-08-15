package io.astronout.core.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val TODAY = 24 * HOUR_MILLIS

/**
 * Convert Date to String with Templated Date Format
 */
fun Date.toString(format: ConverterDate): String {
    return try {
        SimpleDateFormat(
            format.formatter, Locale.KOREA
        ).format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/**
 * Convert Date to String with Custom Date Format
 */
fun Date.toString(format: String): String {
    return try {
        SimpleDateFormat(
            format, Locale.KOREA
        ).format(this)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/**
 * Convert String Date UTC Format to Template Date Format
 */
fun String.convertUTCTimeTo(desireFormat: ConverterDate): String {
    val dateFormat = SimpleDateFormat(ConverterDate.UTC.formatter, Locale.KOREA)
    return try {
        val date = dateFormat.parse(this) ?: Date()
        val hour = 3600 * 1000
        val newDate = Date(date.time + 7 * hour)
        dateFormat.applyPattern(desireFormat.formatter)
        dateFormat.format(newDate)
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun String.convertUTC2TimeTo(desireFormat: ConverterDate): String {
    val dateFormat = SimpleDateFormat(ConverterDate.UTC2.formatter, Locale.KOREA)
    return try {
        val date = dateFormat.parse(this) ?: Date()
        val newDate = Date(date.time)
        dateFormat.applyPattern(desireFormat.formatter)
        dateFormat.format(newDate).toString()
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun String.convertUTC3TimeTo(desireFormat: ConverterDate): String {
    val dateFormat = SimpleDateFormat(ConverterDate.UTC.formatter, Locale.KOREA)
    return try {
        val date = dateFormat.parse(this) ?: Date()
        val hour = 3600 * 1000
        val newDate = Date(date.time + 7 * hour)
        dateFormat.applyPattern(desireFormat.formatter)
        dateFormat.format(newDate).toString()
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

fun String.convertUTCTimezone(desireFormat: ConverterDate, visibleTimeZone: Boolean = true): String {
    val zonedDateTime = ZonedDateTime.parse(this).withZoneSameInstant(ZoneId.systemDefault())
    val timeZoneId = zonedDateTime.format(DateTimeFormatter.ofPattern("zzz", Locale.KOREA))
    val timeZone = if (timeZoneId.contains("GMT") || !visibleTimeZone) String() else " $timeZoneId"
    return DateTimeFormatter.ofPattern(desireFormat.formatter).format(zonedDateTime).plus(timeZone)
}

/**
 * Convert String Datetime into Time 12H Format
 */
fun String.convertUTCToTime(desireFormat: ConverterDate): String {
    val dateFormat = SimpleDateFormat(ConverterDate.UTC.formatter, Locale.KOREA)
    return try {
        val date = dateFormat.parse(this) ?: Date()
        val hour = 3600 * 1000
        val newDate = Date(date.time + 7 * hour)
        dateFormat.applyPattern(desireFormat.formatter)
        val tempResult = dateFormat.format(newDate)
        tempResult.split(" ")[0]
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}

/**
 * Convert Local Date UTC Format to Template Date Format
 */
fun LocalDate.toString(format: ConverterDate): String {
    return format(
        DateTimeFormatter.ofPattern(format.formatter, Locale.KOREA)
    )
}

fun LocalDateTime.toString(format: ConverterDate): String {
    return format(
        DateTimeFormatter.ofPattern(format.formatter, Locale.KOREA)
    )
}

/**
 * Convert Local Date UTC Format to Custom Date Format
 */
fun LocalDate.toString(format: String): String {
    return format(
        DateTimeFormatter.ofPattern(format)
    )
}

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault())
}

fun Long.toInstant(): Instant {
    return Instant.ofEpochMilli(this)
}

fun now(): LocalDateTime {
    return LocalDateTime.now(ZoneId.systemDefault())
}

fun nowUnix(): Long {
    return now().toUnix()
}

fun LocalDateTime.toUnix(): Long {
    return atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun String.toLocalDate(format: String = ConverterDate.UTC.formatter): LocalDate {
    DateTimeFormatter.ofPattern(format).let {
        it.withLocale(Locale.KOREA)
        return LocalDate.parse(this, it)
    }
}

fun LocalDate.toDate(): Date {
    return Date.from(atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun String.toDate(format: String = ConverterDate.UTC.formatter): Date {
    return toLocalDate(format).toDate()
}

fun String.convertDateTo(desireFormat: ConverterDate, originalFormat: ConverterDate = ConverterDate.SQL_FULL_DATE): String {
    val dateFormat = SimpleDateFormat(originalFormat.formatter, Locale.KOREA)
    val date = dateFormat.parse(this) ?: Date()
    dateFormat.applyPattern(desireFormat.formatter)
    return dateFormat.format(date)
}

fun Long.toDateString(format: ConverterDate): String {
    val date = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
    return date.toString(format)
}

fun getCalendar(): Calendar {
    return Calendar.getInstance(TimeZone.getDefault())
}

fun toCalendar(date: Date): Calendar {
    return Calendar.getInstance(TimeZone.getDefault()).apply {
        time = date
    }
}

fun getNextWeekDate(): LocalDate = LocalDate.now().plusWeeks(1)

fun LocalDate.getWeekRange(format: ConverterDate = ConverterDate.SQL_DATE): List<String> {
    val formatter = DateTimeFormatter.ofPattern(format.formatter, Locale.KOREA)
    val startOfWeek = with(DayOfWeek.MONDAY)
    val endOfWeek = with(DayOfWeek.SUNDAY)

    val dates = mutableListOf<String>()
    var date = startOfWeek
    while (!date.isAfter(endOfWeek)) {
        dates.add(date.format(formatter))
        date = date.plusDays(1)
    }
    return dates
}

fun LocalDate.getWeekRangeString(format: ConverterDate = ConverterDate.SIMPLE_SCHEDULE_DATE): String {
    val dates = getWeekRange(format)
    val startDay = dates.first()
    val endDay = dates.last()

    return "$startDay~$endDay"
}

fun Date.isInCurrentWeek(): Boolean {
    val week = getCalendar()[Calendar.WEEK_OF_YEAR]
    val year = getCalendar()[Calendar.YEAR]
    val targetCalendar = getCalendar().apply {
        time = this@isInCurrentWeek
    }
    val targetWeek = targetCalendar[Calendar.WEEK_OF_YEAR]
    val targetYear = targetCalendar[Calendar.YEAR]
    return week == targetWeek && year == targetYear
}

