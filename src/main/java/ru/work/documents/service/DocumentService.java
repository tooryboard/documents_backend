package ru.work.documents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.work.documents.dto.DocumentCreateDto;
import ru.work.documents.dto.DocumentDisableDto;
import ru.work.documents.dto.DocumentDto;
import ru.work.documents.dto.DocumentVersionCreateDto;
import ru.work.documents.dto.DocumentVersionDto;
import ru.work.documents.dto.DocumentWithVersionsDto;
import ru.work.documents.exception.DocumentsRuntimeException;
import ru.work.documents.model.Document;
import ru.work.documents.model.DocumentRegCard;
import ru.work.documents.model.DocumentVersion;
import ru.work.documents.repository.DocumentRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

	private final TransactionHelper transactionHelper;
	private final DocumentRepository documentRepository;

	public List<DocumentDto> getDocuments() {
		return transactionHelper.execute(() -> documentRepository.getDocuments().stream()
				.map(document -> DocumentDto.builder()
						.id(document.getId())
						.name(document.getName())
						.author(document.getAuthor())
						.introDocumentNumber(document.getRegCard().getIntroDocumentNumber())
						.introDateTime(document.getRegCard().getIntroDateTime())
						.externDocumentNumber(document.getRegCard().getExternDocumentNumber())
						.externDateTime(document.getRegCard().getExternDateTime())
						.build())
				.toList());
	}

	public DocumentWithVersionsDto getDocumentWithVersions(long documentId) {
		return transactionHelper.execute(() -> documentRepository.getDocumentWithVersions(documentId)
				.map(document -> DocumentWithVersionsDto.builder()
						.id(document.getId())
						.name(document.getName())
						.versions(document.getVersions().stream()
								.map(documentVersion -> DocumentVersionDto.builder()
										.id(documentVersion.getId())
										.version(documentVersion.getVersion())
										.author(documentVersion.getAuthor())
										.createDateTime(documentVersion.getCreateDateTime())
										.fileName(documentVersion.getFileName())
										.build())
								.toList())
						.build()))
				.orElseThrow(() -> new DocumentsRuntimeException("Document not found", HttpStatus.NOT_FOUND));
	}

	public void createDocument(DocumentCreateDto dto, MultipartFile file) {
		transactionHelper.execute(() -> {
			if (documentRepository.existsByIntroDocumentNumber(dto.introDocumentNumber())) {
				throw new DocumentsRuntimeException("Document with intro number already exists", HttpStatus.BAD_REQUEST);
			}

			final Document document = new Document();
			document.setName(dto.name());
			document.setAuthor(dto.author());

			final DocumentRegCard documentRegCard = document.getRegCard();
			documentRegCard.setDocument(document);
			documentRegCard.setIntroDocumentNumber(dto.introDocumentNumber());

			final DocumentVersion documentVersion = getDocumentVersion(document, 1, dto.author(), file);
			document.getVersions().add(documentVersion);

			documentRepository.save(document);
		});
	}

	public void disableDocument(long documentId, DocumentDisableDto dto) {
		transactionHelper.execute(() -> {
			final Document document = documentRepository.getDocument(documentId)
					.orElseThrow(() -> new DocumentsRuntimeException("Document not found", HttpStatus.NOT_FOUND));

			if (documentRepository.existsByExternDocumentNumber(dto.externDocumentNumber())) {
				throw new DocumentsRuntimeException("Document with extern number already exists", HttpStatus.BAD_REQUEST);
			}

			final DocumentRegCard documentRegCard = document.getRegCard();
			documentRegCard.setExternDocumentNumber(dto.externDocumentNumber());
			documentRegCard.setExternDateTime(LocalDateTime.now());
		});
	}

	public void createDocumentVersion(long documentId, DocumentVersionCreateDto dto, MultipartFile file) {
		transactionHelper.execute(() -> {
			final Document document = documentRepository.getDocument(documentId)
					.orElseThrow(() -> new DocumentsRuntimeException("Document not found", HttpStatus.NOT_FOUND));

			final Integer versionNumber = documentRepository.getLastDocumentVersion(documentId) + 1;
			document.getVersions().add(getDocumentVersion(document, versionNumber, dto.author(), file));
		});
	}

	public HttpEntity<byte[]> downloadDocument(long documentId, int version) {
		return transactionHelper.execute(() -> documentRepository.getDocumentVersion(documentId, version)
				.map(documentVersion -> {
					final HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.valueOf(documentVersion.getContentType()));
					headers.setContentDisposition(ContentDisposition.attachment()
							.filename(documentVersion.getFileName())
							.build());
					return new HttpEntity<>(documentVersion.getContent(), headers);
				})
				.orElseThrow(() -> new DocumentsRuntimeException("Document version not found", HttpStatus.NOT_FOUND)));
	}


	private DocumentVersion getDocumentVersion(Document document, Integer versionNumber, String author, MultipartFile file) {
		final DocumentVersion documentVersion = new DocumentVersion();
		documentVersion.setDocument(document);
		documentVersion.setVersion(versionNumber);
		documentVersion.setAuthor(author);
		documentVersion.setFileName(file.getOriginalFilename());
		documentVersion.setContentType(file.getContentType());
		documentVersion.setContent(getContent(file));
		return documentVersion;
	}

	private byte[] getContent(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (IOException e) {
			throw new DocumentsRuntimeException("Ошибка при загрузке файла", HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
}
