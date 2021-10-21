package org.agrisud.mediathequeapi.cloudservice;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.clouddao.ExpositionCloudDao;
import org.agrisud.mediathequeapi.util.CloudFileHelper;
import org.agrisud.mediathequeapi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpositionCloudService {
	@Autowired
    ExpositionCloudDao expositionCloudDao;
	
	@Autowired
	Utils util;

    public List<String> getFolders() {
        return expositionCloudDao.getEventFolders();
    }

    @Async
    public void createFolder(String path) {
        expositionCloudDao.createFolder(path);
    }

//    public ExpositionCloudService(ExpositionCloudDao expositionCloudDao) {
//        this.expositionCloudDao = expositionCloudDao;
//        if (!isFolderExist("/Mediatheque/ExpositionsImages/")) {
//            this.createFolder("/Mediatheque/ExpositionsImages/");
//        }
//    }

    public List<String> uploadFile(MultipartFile multipartFile, String expositionName) {
        File file = CloudFileHelper.getTempFileFromMultiPartFile(multipartFile);
        List<String> listParams = new ArrayList<>();
        String expositionImageUrl = expositionCloudDao.upLoadFile(file, getFileName(Objects.requireNonNull(multipartFile.getOriginalFilename()), expositionName));
        file.delete();
        listParams.add(expositionImageUrl); // return url image
        listParams.add(getFileName(Objects.requireNonNull(multipartFile.getOriginalFilename()), expositionName));
        return listParams;

    }

    public InputStream getFile(String fileName) throws IOException {
        return expositionCloudDao.getFile(fileName);
    }

    public Boolean isFolderExist(String path) {
        return expositionCloudDao.isFolderExist(path);
    }

    @Async
    public void deleteFolder(String pathFolder) {
        expositionCloudDao.deleteFolder(pathFolder);
    }

    @Async
    public void renameFile(String oldPath, String newPath) {
        expositionCloudDao.renameFile(oldPath, newPath);
    }

    private String getFileName(String originalFilename, String expositionName) {
        String folderName = "/Mediatheque/ExpositionsImages/" + expositionName;
        String fileName = originalFilename.substring(0, originalFilename.indexOf('.')) + util.generateKey(32);
        return folderName + fileName + originalFilename.substring(originalFilename.indexOf('.'));
    }

    @Async
    public void deleteFile(String pathFile) {
        expositionCloudDao.deleteFile(pathFile);
    }
}
