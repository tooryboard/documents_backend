package ru.work.documents.dto;

import lombok.Builder;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DocumentWithVersionsDto(Long id,
									  String name,
									  List<DocumentVersionDto> versions) {
}
