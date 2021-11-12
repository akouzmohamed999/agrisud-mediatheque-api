package org.agrisud.mediathequeapi.service;

import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.cloudservice.ThematicFolderCloudService;
import org.agrisud.mediathequeapi.dao.ThematicFolderDao;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.ThematicFolder;
import org.agrisud.mediathequeapi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ThematicFolderService {
	@Autowired
	ThematicFolderCloudService thematicFolderCloudService;
	@Autowired
	ThematicFolderDao thematicFolderDao;
	@Autowired
	Utils util;
	
	public void addThematicFolder(ThematicFolder thematicFolder) {
		thematicFolder.setUrlImage(thematicFolderCloudService.doShared(thematicFolder.getPathImage()) + "/preview");
		thematicFolderDao.addThematicFolder(thematicFolder);
	}
	
	public String uploadFile(MultipartFile multipartFile, String path) {
		String generatedKey = util.generateKey(32);
		return thematicFolderCloudService.uploadFile(multipartFile,path , generatedKey);
		
	}

	public List<ThematicFolder> getThematicFolderByParentId(Long parentId) {
		return thematicFolderDao.getThematicFolderByParentId(parentId);
	}

	public String updateFiles(MultipartFile multipartFile, String path) {
		String generatedKey = util.generateKey(32);
		return thematicFolderCloudService.updateFiles(multipartFile,path,generatedKey);
	}
	
	public int updateThematicFolder(ThematicFolder thematicFolder) {
		thematicFolder.setUrlImage(thematicFolderCloudService.doShared(thematicFolder.getPathImage()) + "/preview");
		return thematicFolderDao.updateThematicFolder(thematicFolder);
	}

	public int deleteThematicFolderById(Long id) {
		Optional<ThematicFolder> thematicFolder = thematicFolderDao.getThematicFolderById(id);
		if(thematicFolder.isPresent() && !thematicFolder.isEmpty()) {
			thematicFolderCloudService.deleteFile(thematicFolder.get().getPathImage());
			return thematicFolderDao.deleteThematicFolder(id);
		}
		return 0;
	}

	public List<ThematicFolder> getAllParent(Long parentId) {
		return thematicFolderDao.getAllParent(parentId);
	}
}
