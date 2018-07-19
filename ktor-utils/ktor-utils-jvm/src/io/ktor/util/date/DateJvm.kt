package io.ktor.util.date

import java.util.*

private val GMT_TIMEZONE = TimeZone.getTimeZone("GMT")

actual fun GMTDate(timestamp: Long?): GMTDate = Calendar.getInstance(GMT_TIMEZONE, Locale.ROOT)!!.toGMTDate(timestamp)

actual fun GMTDate(
    seconds: Int, minutes: Int, hours: Int, dayOfMonth: Int, month: Month, year: Int
): GMTDate = (Calendar.getInstance(GMT_TIMEZONE, Locale.ROOT)!!).apply {
    set(Calendar.SECOND, seconds)
    set(Calendar.MINUTE, minutes)
    set(Calendar.HOUR, hours)

    set(Calendar.DAY_OF_MONTH, dayOfMonth)

    set(Calendar.MONTH, month.ordinal)
    set(Calendar.YEAR, year)
}.toGMTDate(timestamp = null)

/**
 * Convert to [Date]
 */
fun GMTDate.toJvmDate(): Date = Calendar.getInstance(GMT_TIMEZONE, Locale.ROOT)!!.time!!

/**
 * Convert [Callendar] instance 
 */
fun Calendar.toGMTDate(timestamp: Long?): GMTDate {
    timestamp?.let { timeInMillis = it }

    val seconds = get(Calendar.SECOND)
    val minutes = get(Calendar.MINUTE)
    val hours = get(Calendar.HOUR_OF_DAY)

    /**
     * from (SUN 1) (MON 2) .. (SAT 7) to (SUN 6) (MON 0) .. (SAT 5)
     */
    val numberOfDay = (get(Calendar.DAY_OF_WEEK) + 7 - 2) % 7
    val dayOfWeek = WeekDay.from(numberOfDay)

    val dayOfMonth = get(Calendar.DAY_OF_MONTH)
    val dayOfYear = get(Calendar.DAY_OF_YEAR)

    val month = Month.from(get(Calendar.MONTH))
    val year = get(Calendar.YEAR)

    return GMTDate(
        seconds, minutes, hours,
        dayOfWeek, dayOfMonth, dayOfYear,
        month, year,
        timeInMillis
    )
}
