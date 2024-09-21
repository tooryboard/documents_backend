package ru.work.documents.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
	@Override
	public void serialize(LocalDateTime dateTime, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
		jGen.writeString(dateTime.atOffset(OffsetDateTime.now().getOffset()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
	}
}
