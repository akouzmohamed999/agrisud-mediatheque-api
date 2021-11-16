package org.agrisud.mediathequeapi.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.dao.CategoryDao;
import org.agrisud.mediathequeapi.dao.ExpositionImageDao;
import org.agrisud.mediathequeapi.dao.ListCountrySupportDao;
import org.agrisud.mediathequeapi.dao.ListCountrySupportVideoDao;
import org.agrisud.mediathequeapi.dao.ListExpositionImageDao;
import org.agrisud.mediathequeapi.dao.ListThematicExpositionDao;
import org.agrisud.mediathequeapi.dao.ListThematicSupportDao;
import org.agrisud.mediathequeapi.dao.ListThematicSupportVideoDao;
import org.agrisud.mediathequeapi.dao.SupportDao;
import org.agrisud.mediathequeapi.dao.SupportVideoDao;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.Exposition;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.model.SupportVideo;
import org.agrisud.mediathequeapi.search.ExpositionSearchRepository;
import org.agrisud.mediathequeapi.search.SupportSearchRepository;
import org.agrisud.mediathequeapi.search.SupportVideoSearchRepository;
import org.agrisud.mediathequeapi.util.Utils;
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
	SupportDao supportDao;
	@Autowired
	SupportVideoDao supportVideoDao;
	@Autowired
	ExpositionService expositionService;
	@Autowired
	Utils util;
	@Autowired
	ListThematicSupportDao listThematicSupportDao;
	@Autowired
	ListCountrySupportDao listCountrySupportDao;
	@Autowired
	ListThematicSupportVideoDao listThematicSupportVideoDao;
	@Autowired
	ListCountrySupportVideoDao listCountrySupportVideoDao;
	@Autowired
	ListThematicExpositionDao listThematicExpositionDao;
	@Autowired
	ExpositionImageService expositionImageService;
	@Autowired
	SupportSearchRepository supportSearchRepository;
	@Autowired
	SupportVideoSearchRepository supportVideoSearchRepository;
	@Autowired
	ExpositionImageDao expositionImageDao;
	@Autowired
	ListExpositionImageDao listExpositionImageDao;
	@Autowired
	ExpositionSearchRepository expositionSearchRepository;
	@Autowired
	ThematicFolderService thematicFolderService;
	
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
			if("0".equals(category.get().getTypeCategory())) {
				for(Support support: supportDao.getListAllSupport(id)) {
					if(support.getPathImage()!= null && !"".equals(support.getPathImage())) {
						eventCloudService.deleteFile(support.getPathImage());
					}
					listThematicSupportDao.deleteListThematicBySupportId(support.getSupportId());
					listCountrySupportDao.deleteListCounrtyBySupportId(support.getSupportId());
					supportSearchRepository.delete(supportSearchRepository.findOneBySupportId(support.getSupportId()));
				}
				supportDao.deleteSupportByCategoryId(id);
			}else if("1".equals(category.get().getTypeCategory())) {
				for(SupportVideo supportVideo: supportVideoDao.getListSupportVideo(id)) {
					if(supportVideo.getPathSupport()!= null && supportVideo.getPathSupport() != "") {
						eventCloudService.deleteFile(supportVideo.getPathSupport());
					}
					listThematicSupportVideoDao.deleteListThematicBySupportVideoId(supportVideo.getSupportId());
					listCountrySupportVideoDao.deleteListCounrtyBySupportVideoId(supportVideo.getSupportId());
					supportVideoSearchRepository.delete(supportVideoSearchRepository.findOneBySupportId(supportVideo.getSupportId()));
				}
				supportVideoDao.deleteSupportVideoByCategoryId(id);
			}else if("2".equals(category.get().getTypeCategory())) {
				for(Exposition exposition : expositionService.getAllExpositionByCategory(id)) {
					listThematicExpositionDao.deleteListThematicByExpositionId(exposition.getExpositionId());
					expositionService.deleteExposition(exposition.getExpositionId());
				}
			}else {
				thematicFolderService.getThematicFolderByParentId(Long.valueOf(0), category.get().getCategoryId())
					.forEach(folder->{
						thematicFolderService.deleteThematicFolderById(folder.getThematicFolderId());
					});
			}
			
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

	public Boolean checkTitleIfExist(String pathFolder, String title,String type) {
		if(categoryDao.checkTitleIfExist(pathFolder,title,type).size() == 0) {
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
		if(!category.getPathFolder().equals(path + category.getTitleAnglais())) {
			eventCloudService.renameFile(category.getPathFolder(), path + category.getTitleAnglais());
		}
		
		category.setPathFolder(path + category.getTitleAnglais());
		return categoryDao.updateCategory(category);
	}
}
