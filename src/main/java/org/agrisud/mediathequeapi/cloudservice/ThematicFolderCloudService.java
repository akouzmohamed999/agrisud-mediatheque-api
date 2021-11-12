package org.agrisud.mediathequeapi.cloudservice;

import java.io.File;
import java.util.Optional;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.clouddao.ThematicFolderCloudDao;
import org.agrisud.mediathequeapi.util.CloudFileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ThematicFolderCloudService {
	@Autowired
	ThematicFolderCloudDao thematicFolderCloudDao;
	
	public String doShared(String path) {
		return thematicFolderCloudDao.doShared(path);
	}
	
	public String uploadFile(MultipartFile multipartFile, String path, String generatedKey) {
		String[] name = multipartFile.getOriginalFilename().split("\\.");
		String fullFilePath = path.concat(name[0] + "_" + generatedKey + "." + name[1]);
		Optional<File> fileOptional = Optional.ofNullable(CloudFileHelper.getTempFileFromMultiPartFile(multipartFile));
		fileOptional.ifPresent(file -> {
			thematicFolderCloudDao.upLoadFile(file, fullFilePath);
			file.delete();
		});
		return fullFilePath;
	}

	public String updateFiles(MultipartFile multipartFile, String path,String generatedKey) {
		String[] name = multipartFile.getOriginalFilename().split("\\.");
		String fullFilePath = "/Mediatheque/image/".concat(name[0] + "_" + generatedKey + "." + name[1]);
		Optional<File> fileOptional = Optional.ofNullable(CloudFileHelper.getTempFileFromMultiPartFile(multipartFile));
		thematicFolderCloudDao.deleteFile(path);
		fileOptional.ifPresent(file -> {
			thematicFolderCloudDao.upLoadFile(file, fullFilePath);
			file.delete();
		});
		return fullFilePath;
	}

	@Async
	public void deleteFile(String pathFile) {
		thematicFolderCloudDao.deleteFile(pathFile);
	}
}
