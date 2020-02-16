package com.srit.otachy.adapters

import com.google.gson.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.Exception
import java.lang.reflect.Type
import java.util.*


val dateTimeBackendFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
val dateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH)


class LocalDateTimeConverter : JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime = try {
        if (json.asString.contains('T'))
            LocalDateTime.parse(json.asString, dateTimeBackendFormatter)
        else LocalDateTime.of(
            LocalDate.parse(json.asString, dateTimeFormatter),
            LocalTime.now()
        )
    } catch (e: Exception) {
        LocalDateTime.now()
    }

    override fun serialize(
        src: LocalDateTime,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonPrimitive(src.format(dateTimeFormatter))

}