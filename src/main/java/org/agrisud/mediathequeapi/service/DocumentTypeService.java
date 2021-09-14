package org.agrisud.mediathequeapi.service;

import java.util.List;

import org.agrisud.mediathequeapi.dao.DocumentTypeDao;
import org.agrisud.mediathequeapi.model.DocumentType;
import org.agrisud.mediathequeapi.model.Thematic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentTypeService {
	@Autowired
	DocumentTypeDao documentTypeDao;
	public int addDocumentType(DocumentType docmentType) {
		return documentTypeDao.addDocumentType(docmentType);
	}
	public int deleteDocumentType(Long id) {
		return documentTypeDao.deleteDocumentType(id);
	}
	public int updateDocumentType(DocumentType documentType) {
		return documentTypeDao.updateDocumentType(documentType);
	}
	public List<DocumentType> getAllDocumentType() {
		return documentTypeDao.getAllDocumentType();
	}
	public DocumentType getDocumentTypeById(Long id) {
		return documentTypeDao.getDocumentTypeById(id);
	}

}
