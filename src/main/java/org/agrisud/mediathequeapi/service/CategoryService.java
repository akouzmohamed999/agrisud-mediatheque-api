package org.agrisud.mediathequeapi.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.dao.CategoryDao;
import org.agrisud.mediathequeapi.dao.SupportDao;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.util.Utils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoryService {
	@Autowired
    CategoryDao categoryDao;
	@Autowired
    EventCloudService eventCloudService;
	@Autowired
	SupportDao supportDao;
	@Autowired
	Utils util;
	
	public void addCategory(Category category) {
		//Boolean isFileExiste =false; 
		//Boolean isFileExiste = eventCloudService.isFolderExist(category.getPathFolder());
//		for(String folder :eventCloudService.getFolders()) {
//			if(category.getTitle().equals(folder)) {
//				isFileExiste= true;
//			}
//		}
		//if(!isFileExiste) {
			eventCloudService.createFolder(category.getPathFolder());
			
			category.setUrlImage(eventCloudService.doShared(category.getPathImage()) + "/preview");
			categoryDao.addCategory(category);
		//}
		
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
			for(Category level : getChildOfLevel(category.get().getPathFolder() + "/")) {
				eventCloudService.deleteFile(level.getPathImage());
			}
			
			eventCloudService.deleteFolder(category.get().getPathFolder());
			eventCloudService.deleteFile(category.get().getPathImage());
			supportDao.deleteSupportByCategoryId(id);
			return categoryDao.deleteCategory(category.get().getPathFolder(),id);
		}
		return 0;
	}

	public Optional<Category> getCategoryById(Long id) {
		return categoryDao.getCategoryById(id);
	}

	public List<Category> getCategoryByTitle(String title, String path) {
		List<Category> list = categoryDao.getCategoryByTitle(title, path);
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

	public String updateFiles(MultipartFile multipartFile, String path) {
		String generatedKey = util.generateKey(32);
		return eventCloudService.updateFiles(multipartFile,path,generatedKey);
	}

	public int updateCategory(Category category) {
		category.setUrlImage(eventCloudService.doShared(category.getPathImage()) + "/preview");
		String name[] =category.getPathFolder().split("/");
		String path = "/";
		for(int i=1; i< name.length - 1;i++) {
			path+=name[i]+ "/";
		}
		if(!category.getPathFolder().equals(path + category.getTitle())) {
			eventCloudService.renameFile(category.getPathFolder(), path + category.getTitle());
		}
		
		category.setPathFolder(path + category.getTitle());
		return categoryDao.updateCategory(category);
	}
}
