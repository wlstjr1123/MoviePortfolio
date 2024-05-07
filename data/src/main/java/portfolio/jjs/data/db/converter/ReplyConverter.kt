package portfolio.jjs.data.db.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ReplyConverter {
    @TypeConverter
    fun fromZonedDateTime(value: ZonedDateTime): String {
        return DateTimeFormatter.ISO_ZONED_DATE_TIME.format(value)
    }

    @TypeConverter
    fun toZonedDateTime(value: String): ZonedDateTime {
        return ZonedDateTime.parse(value)
    }
}