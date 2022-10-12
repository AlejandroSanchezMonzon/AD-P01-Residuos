package utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

fun dateFormatter(date: LocalDateTime):String{
    return date.format(
        DateTimeFormatter.ofLocalizedDate
        (FormatStyle.FULL).withLocale(Locale("es", "ES")))
}