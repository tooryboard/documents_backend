package ru.work.documents.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.work.documents.model.Document;
import ru.work.documents.model.DocumentVersion;
import ru.work.documents.repository.DocumentRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class DocumentEntityMangerImpl implements DocumentRepository {

	private final EntityManager entityManager;

	@Override
	public void save(Document document) {
		entityManager.persist(document);
	}

	@Override
	public Optional<Document> getDocument(long id) {
		return Optional.ofNullable(entityManager.find(Document.class, id));
	}

	@Override
	public Optional<Document> getDocumentWithVersions(long id) {
		try {
			return Optional.ofNullable(entityManager.createQuery(
							"""
							SELECT d
							FROM Document d JOIN FETCH d.versions
							WHERE d.id =: id
							""",
							Document.class)
					.setParameter("id", id)
					.getSingleResult());
		} catch (NoResultException e) {
			log.error("Document not found", e);
			return Optional.empty();
		}
	}

	@Override
	public Optional<DocumentVersion> getDocumentVersion(long id, int version) {
		try {
			return Optional.ofNullable(entityManager.createQuery(
							"""
							SELECT dv
							FROM DocumentVersion dv
							WHERE dv.document.id =: id AND dv.version = :version
							""",
							DocumentVersion.class)
					.setParameter("id", id)
					.setParameter("version", version)
					.getSingleResult());
		} catch (NoResultException e) {
			log.error("Document version not found", e);
			return Optional.empty();
		}
	}

	@Override
	public Integer getLastDocumentVersion(long id) {
		return entityManager.createQuery(
						"""
						SELECT dv.version
						FROM DocumentVersion dv
						WHERE dv.document.id =: id
						ORDER BY dv.version DESC
						LIMIT 1
						""",
						Integer.class)
				.setParameter("id", id)
				.getSingleResult();
	}

	@Override
	public List<Document> getDocuments() {
		return entityManager.createQuery("SELECT d FROM Document d ORDER BY id", Document.class).getResultList();
	}

	@Override
	public boolean existsByIntroDocumentNumber(String introDocumentNumber) {
		try {
			entityManager.createQuery("SELECT 1 FROM DocumentRegCard rc WHERE rc.introDocumentNumber = :introDocumentNumber")
					.setParameter("introDocumentNumber", introDocumentNumber)
					.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public boolean existsByExternDocumentNumber(String externDocumentNumber) {
		try {
			entityManager.createQuery("SELECT 1 FROM DocumentRegCard rc WHERE rc.externDocumentNumber = :externDocumentNumber")
					.setParameter("externDocumentNumber", externDocumentNumber)
					.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
}
