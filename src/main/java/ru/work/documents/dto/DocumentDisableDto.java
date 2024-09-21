package ru.work.documents.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DocumentDisableDto(@NotNull String externDocumentNumber) {
}
