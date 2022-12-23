package database

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class InstantConverters {
    @TypeConverter
    fun from(value: String): Instant = Instant.parse(value)

    @TypeConverter
    fun to(instant: Instant): String = instant.toString()
}
