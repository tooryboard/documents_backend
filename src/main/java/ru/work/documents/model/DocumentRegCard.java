package ru.work.documents.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "document_reg_card")
public class DocumentRegCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "document_id", referencedColumnName = "id", nullable = false)
	private Document document;

	@Column(name = "intro_document_number", nullable = false, updatable = false, unique = true)
	private String introDocumentNumber;

	@Column(name = "intro_date_time", nullable = false, updatable = false)
	private LocalDateTime introDateTime = LocalDateTime.now();

	@Nullable
	@Column(name = "extern_document_number", unique = true)
	private String externDocumentNumber;

	@Nullable
	@Column(name = "extern_date_time")
	private LocalDateTime externDateTime;

}
