package org.agrisud.mediathequeapi.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.dao.CategoryDao;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.util.Utils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoryService {
	@Autowired
    CategoryDao categoryDao;
	@Autowired
    EventCloudService eventCloudService;
	@Autowired
	Utils util;
	
	public void addCategory(Category category) {
		//Boolean isFileExiste =false; 
		Boolean isFileExiste = eventCloudService.isFolderExist(category.getPathFolder());
//		for(String folder :eventCloudService.getFolders()) {
//			if(category.getTitle().equals(folder)) {
//				isFileExiste= true;
//			}
//		}
		if(!isFileExiste) {
			eventCloudService.createFolder(category.getPathFolder());
			category.setUrlImage(eventCloudService.doShared(category.getPathImage()) + "/preview");
			categoryDao.addCategory(category);
		}
		
	}

	public String uploadFile(MultipartFile multipartFile, String path) {
		String generatedKey = util.generateKey(32);
		return eventCloudService.uploadFile(multipartFile,path , generatedKey);
		
	}

	public InputStream getFile(String fileName) throws IOException {
		return eventCloudService.getFile(fileName);
	}

	public List<Category> getAllCategory() throws IOException {
		List<Category> listCategory = categoryDao.getAllCategory();
//		for(Category category : listCategory) {
//			byte[] media =  IOUtils.toByteArray(eventCloudService.getFile(category.getPathImage()));
//			String imageStr = Base64.encodeBase64String(media);
//			category.setImage(imageStr);
//		}
		return listCategory;
	}

	public int deleteCategory(Long id) {
		Optional<Category> category = getCategoryById(id);
		if(category.isPresent() && !category.isEmpty()) {
			eventCloudService.deleteFolder(category.get().getPathFolder());
			eventCloudService.deleteFile(category.get().getPathImage());
			return categoryDao.deleteCategory(category.get().getPathFolder());
		}
		return 0;
	}

	public Optional<Category> getCategoryById(Long id) {
		return categoryDao.getCategoryById(id);
	}

	public List<Category> getCategoryByTitle(String title) {
		List<Category> list = categoryDao.getCategoryByTitle(title);
		return list;
	}

	public Boolean checkTitleIfExist(String pathFolder, String title) {
		List<Category> list = categoryDao.checkTitleIfExist(pathFolder,title);
		if(categoryDao.checkTitleIfExist(pathFolder,title).size() == 0) {
			return false;
		}
		return true;
	}

	public List<Category> getChildOfLevel(String pathFolder) {
		return categoryDao.getChildOfLevel(pathFolder);
	}
}
