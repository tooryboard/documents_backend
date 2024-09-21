package ru.work.documents.repository;

import ru.work.documents.model.Document;
import ru.work.documents.model.DocumentVersion;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository {

	void save(Document document);

	Optional<Document> getDocument(long id);

	Optional<Document> getDocumentWithVersions(long id);

	Optional<DocumentVersion> getDocumentVersion(long id, int version);

	Integer getLastDocumentVersion(long id);

	List<Document> getDocuments();

	boolean existsByIntroDocumentNumber(String introDocumentNumber);

	boolean existsByExternDocumentNumber(String externDocumentNumber);

}
