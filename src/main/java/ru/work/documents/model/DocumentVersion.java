package ru.work.documents.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "document_version",
		indexes = @Index(name = "document_document_id_version_unique_index", columnList = "document_id, version", unique = true))
public class DocumentVersion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_id", nullable = false)
	private Document document;

	@Column(nullable = false)
	private Integer version;

	@Column(nullable = false)
	private String author;

	@Column(nullable = false)
	private LocalDateTime createDateTime = LocalDateTime.now();

	@Column(nullable = false)
	private String contentType;

	@Column(nullable = false)
	private String fileName;

	@Column(nullable = false)
	private byte[] content;

}
