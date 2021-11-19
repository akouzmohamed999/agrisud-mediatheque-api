package org.agrisud.mediathequeapi.service;

import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.cloudservice.ThematicFolderCloudService;
import org.agrisud.mediathequeapi.dao.ThematicFolderDao;
import org.agrisud.mediathequeapi.dao.ThematicFolderMediaDao;
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
	ThematicFolderMediaDao thematicFolderMediaDao;
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

	public List<ThematicFolder> getThematicFolderByParentId(Long parentId,Long categoryId) {
		return thematicFolderDao.getThematicFolderByParentId(parentId,categoryId);
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
		List<ThematicFolder> listChild = getThematicFolderByParentId(id,thematicFolder.get().getCategoryId());
		listChild.forEach(child ->{
			deleteThematicFolderById(child.getThematicFolderId());
		});
		
		if(thematicFolder.isPresent() && !thematicFolder.isEmpty()) {
			thematicFolderCloudService.deleteFile(thematicFolder.get().getPathImage());
			thematicFolderMediaDao.deleteThematicFolderByParentId(id);
			return thematicFolderDao.deleteThematicFolder(id);
		}
		return 0;
	}

	public List<ThematicFolder> getAllParent(Long parentId,Long categoryId) {
		return thematicFolderDao.getAllParent(parentId,categoryId);
	}
}
