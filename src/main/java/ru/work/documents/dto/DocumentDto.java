package ru.work.documents.dto;

import lombok.Builder;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Builder
public record DocumentDto(Long id,
						  String name,
						  String author,
						  String introDocumentNumber,
						  LocalDateTime introDateTime,
						  @Nullable String externDocumentNumber,
						  @Nullable LocalDateTime externDateTime) {
}
