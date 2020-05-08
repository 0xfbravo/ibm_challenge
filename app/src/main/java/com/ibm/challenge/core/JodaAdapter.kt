package com.ibm.challenge.core

import com.google.gson.*
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import java.lang.reflect.Type


class JodaAdapter : JsonSerializer<DateTime?>,
    JsonDeserializer<DateTime?> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): DateTime {
        return DateTime.parse(json.asString)
    }

    override fun serialize( src: DateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(ISODateTimeFormat.dateTimeNoMillis().print(src))
    }
}