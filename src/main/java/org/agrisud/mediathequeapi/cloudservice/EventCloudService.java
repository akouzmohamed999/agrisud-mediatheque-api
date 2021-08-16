package org.agrisud.mediathequeapi.cloudservice;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.util.CloudFileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class EventCloudService {

    @Autowired
    EventCloudDao eventCloudDao;

    public List<String> getFolders() {
        return eventCloudDao.getEventFolders();
    }

    public void uploadFile(MultipartFile multipartFile, String path) {
        String fullFilePath = path.concat(multipartFile.getOriginalFilename());
        Optional<File> fileOptional = Optional.ofNullable(CloudFileHelper.getTempFileFromMultiPartFile(multipartFile));
        fileOptional.ifPresent(file -> {
            eventCloudDao.upLoadFile(file, fullFilePath);
            file.delete();
        });
    }
}
