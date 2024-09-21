package ru.work.documents.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DocumentVersionDto(Long id,
								 Integer version,
								 String author,
								 LocalDateTime createDateTime,
								 String fileName) {
}