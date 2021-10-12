package org.agrisud.mediathequeapi.controller;

import java.io.IOException;
import java.util.List;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.model.DocumentType;
import org.agrisud.mediathequeapi.model.Thematic;
import org.agrisud.mediathequeapi.service.CategoryService;
import org.agrisud.mediathequeapi.service.DocumentTypeService;
import org.agrisud.mediathequeapi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/document")
public class DocumentTypeController {
	@Autowired
	DocumentTypeService documentTypeService;
	@Autowired
	Utils util;
	@Autowired
	EventCloudService eventCloudService;

	@PostMapping
	public int addDocumentType(@RequestBody DocumentType docmentType) {
		return documentTypeService.addDocumentType(docmentType);
	}
	
	@DeleteMapping(path ="/{id}")
	public int deleteDocumentType(@PathVariable("id") Long id) {
		DocumentType document  = documentTypeService.getDocumentTypeById(id);
		eventCloudService.deleteFile(document.getPathImage());
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
	
	@PostMapping(path = "/files" ,consumes = { MediaType.APPLICATION_JSON_VALUE, "multipart/form-data" })
	@ResponseStatus(HttpStatus.OK)
	public String uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam("path") String path) throws IOException {
		String generatedKey = util.generateKey(32);
		return eventCloudService.uploadFile(multipartFile,path,generatedKey);
    }
}
