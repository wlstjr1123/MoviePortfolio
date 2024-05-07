package portfolio.jjs.presentation.utils

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TimeConverterUtils {
    const val REPLY_DB_TIME = "yyyy-MM-dd HH:mm:ss"

    fun toString(value: ZonedDateTime, format: String = ""): String {
        return if (format == "") {
            DateTimeFormatter.ISO_ZONED_DATE_TIME.format(value)
        } else {
            DateTimeFormatter.ofPattern(format).format(value)
        }

    }

    fun toZonedDateTime(value: String, format: String = ""): ZonedDateTime {
        return if (format == "") {
            ZonedDateTime.parse(value)
        } else {
            ZonedDateTime.parse(value, DateTimeFormatter.ofPattern(format))
        }
    }
}