package io.astronout.core.utils

enum class ConverterDate(val formatter: String) {
    TIME_ONLY("HH:mm aa"),
    DATE_ONLY("dd"),
    FULL_DATE("dd MMMM yyyy"),
    FULL_DATE_TIME("dd MMMM yyyy HH:mm"),
    SHORT_DATE("dd MMM yyyy"),
    SIMPLE_DATE("dd/MM/yyyy"),
    YEAR_TO_DATE("yyyy/MM/dd"),
    SIMPLE_DATE_TIME("dd/MM/yyyy HH:mm"),
    SQL_DATE("yyyy-MM-dd"),
    SQL_FULL_DATE("yyyy-MM-dd HH:mm:ss"),
    SIMPLE_DAY("EEE"),
    SIMPLE_MONTH("MMM"),
    SIMPLE_DAY_MONTH("dd MMMM"),
    UTC2("yyyy-MM-dd'T'HH:mm:ss'Z'"),
    UTC3("yyyy-MM-dd'T'HH:mm:SSS'Z'"),
    UTC4("yyyy-MM-dd'T'HH:mm:ss.SSSSS'Z'"),
    UTC("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"),
    UTC_DETAIL("yyyy-MM-dd'T'HH:mm:ss.SSS+HH:mm'Z'"),
    SIMPLE_DAY_DATE("EEEE, dd MMM yyyy"),
    SIMPLE_DATE_TIME_DOT("dd MMM yyyy • HH:mm"),
    SCHEDULE_DATE("yyyy.MM.dd"),
    SIMPLE_SCHEDULE_DATE("MM.dd")
}
