package io.ktor.util.date

import kotlinx.cinterop.*
import platform.posix.*

actual fun GMTDate(timestamp: Long?): GMTDate = memScoped {
    val current: Long = timestamp ?: time(alloc<LongVar>().ptr)
    val dateInfo = alloc<tm>()
    gmtime_r(current.toCPointer(), dateInfo.ptr)

    with(dateInfo) {
        // from (SUN, 0) to (SUN, 6)
        val weekDay = (tm_wday + 7 - 1) % 7
        val year = tm_year + 1900

        GMTDate(
            tm_sec, tm_min, tm_hour,
            WeekDay.from(weekDay), tm_mday, tm_yday,
            Month.from(tm_mon), year, current
        )
    }
}

actual fun GMTDate(
    seconds: Int, minutes: Int, hours: Int, dayOfMonth: Int, month: Month, year: Int
): GMTDate = memScoped {
    val nativeYear = year - 1900

    val dateInfo = alloc<tm>().apply {
        tm_sec = seconds
        tm_min = minutes
        tm_hour = hours

        tm_mday = dayOfMonth

        tm_mon = month.ordinal
        tm_year = nativeYear

        tm_isdst = 0
    }

    val timestamp: Long = mktime(dateInfo.ptr)

    return GMTDate(timestamp)
}
