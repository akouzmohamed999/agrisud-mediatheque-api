package org.agrisud.mediathequeapi.controller;

import java.util.List;

import org.agrisud.mediathequeapi.model.DocumentType;
import org.agrisud.mediathequeapi.model.Thematic;
import org.agrisud.mediathequeapi.service.CategoryService;
import org.agrisud.mediathequeapi.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document")
public class DocumentTypeController {
	@Autowired
	DocumentTypeService documentTypeService;
	

	@PostMapping
	public int addDocumentType(@RequestBody DocumentType docmentType) {
		return documentTypeService.addDocumentType(docmentType);
	}
	
	@DeleteMapping(path ="/{id}")
	public int deleteDocumentType(@PathVariable("id") Long id) {
		return documentTypeService.deleteDocumentType(id);
	}
	
	@PutMapping
	public int updateDocumentType(@RequestBody DocumentType documentType) {
		return documentTypeService.updateDocumentType(documentType);
	}
	
	@GetMapping
	public List<DocumentType> getAllDocumentType() {
		return documentTypeService.getAllDocumentType();
	}
	@GetMapping(path = "/{id}")
	public DocumentType getDocumentTypeById(@PathVariable("id") Long id) {
		return documentTypeService.getDocumentTypeById(id);
	}
}
