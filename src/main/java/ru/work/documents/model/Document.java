package ru.work.documents.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "document")
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String author;

	@Setter(AccessLevel.NONE)
	@OneToOne(mappedBy = "document", optional = false, fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	private DocumentRegCard regCard = new DocumentRegCard();

	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "document", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<DocumentVersion> versions = new ArrayList<>();

}
