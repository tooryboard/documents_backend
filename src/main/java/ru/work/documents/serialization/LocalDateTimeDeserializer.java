package ru.work.documents.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
	@Override
	public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		if (jp.getText().isEmpty()) {
			return null;
		}
		return OffsetDateTime.parse(jp.getText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME).withOffsetSameInstant(OffsetDateTime.now().getOffset()).toLocalDateTime();
	}
}
