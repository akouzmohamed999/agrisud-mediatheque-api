package org.agrisud.mediathequeapi.service;

import java.util.List;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.dao.DocumentTypeDao;
import org.agrisud.mediathequeapi.model.DocumentType;
import org.agrisud.mediathequeapi.model.Thematic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentTypeService {
	@Autowired
	DocumentTypeDao documentTypeDao;
	@Autowired
	EventCloudDao eventCloudDao;
	public int addDocumentType(DocumentType docmentType) {
		docmentType.setUrlImage(eventCloudDao.doShared(docmentType.getPathImage()));
		return documentTypeDao.addDocumentType(docmentType);
	}
	public int deleteDocumentType(Long id) {
		return documentTypeDao.deleteDocumentType(id);
	}
	public int updateDocumentType(DocumentType documentType) {
		DocumentType documentTypeOld = documentTypeDao.getDocumentTypeById(documentType.getDocumentTypeId());
		if(!documentType.getPathImage().equals(documentTypeOld.getPathImage())) {
			eventCloudDao.deleteFile(documentTypeOld.getPathImage());
			documentType.setUrlImage(eventCloudDao.doShared(documentType.getPathImage()));
		}
		return documentTypeDao.updateDocumentType(documentType);
	}
	public List<DocumentType> getAllDocumentType() {
		return documentTypeDao.getAllDocumentType();
	}
	public DocumentType getDocumentTypeById(Long id) {
		return documentTypeDao.getDocumentTypeById(id);
	}

}
