package ru.work.documents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.work.documents.dto.DocumentCreateDto;
import ru.work.documents.dto.DocumentDisableDto;
import ru.work.documents.dto.DocumentVersionCreateDto;
import ru.work.documents.service.DocumentService;

@Validated
@RequiredArgsConstructor
@Controller
@RequestMapping("/documents")
public class DocumentController {

	private final DocumentService documentService;

	@GetMapping
	public String getDocuments(@ModelAttribute("model") ModelMap model) {
		model.addAttribute("documents", documentService.getDocuments());
		return "documents";
	}

	@PostMapping
	public String createDocument(@Validated DocumentCreateDto dto, @RequestPart("file") MultipartFile file) {
		documentService.createDocument(dto, file);
		return "redirect:/documents";
	}

	@PostMapping("{documentId}/disable")
	public String disableDocument(@PathVariable("documentId") long documentId, @Validated DocumentDisableDto dto) {
		documentService.disableDocument(documentId, dto);
		return "redirect:/documents";
	}

	@GetMapping("{documentId}/versions")
	public String getDocumentVersions(@PathVariable("documentId") long documentId, @ModelAttribute("model") ModelMap model) {
		model.addAttribute("document", documentService.getDocumentWithVersions(documentId));
		return "document-versions";
	}

	@PostMapping("{documentId}/versions")
	public String createDocumentVersion(@PathVariable("documentId") long documentId,
										@Validated DocumentVersionCreateDto dto,
										@RequestPart("file") MultipartFile file) {
		documentService.createDocumentVersion(documentId, dto, file);
		return "redirect:/documents/" + documentId + "/versions";
	}

	@GetMapping("{documentId}/versions/{version}/download")
	public HttpEntity<byte[]> downloadDocument(@PathVariable("documentId") long documentId,
								 @PathVariable("version") int version) {
		return documentService.downloadDocument(documentId, version);
	}
}
