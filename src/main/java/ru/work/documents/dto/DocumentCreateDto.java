package ru.work.documents.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DocumentCreateDto(@NotNull String name,
								@NotNull String author,
								@NotNull String introDocumentNumber) {
}
