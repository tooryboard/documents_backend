package ru.work.documents.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DocumentVersionCreateDto(@NotNull String author) {
}
