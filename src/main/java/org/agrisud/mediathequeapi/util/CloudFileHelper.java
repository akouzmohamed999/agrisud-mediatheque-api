package org.agrisud.mediathequeapi.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class CloudFileHelper {
    public static File getTempFileFromMultiPartFile(MultipartFile multipartFile) {
        String fullFileName = multipartFile.getOriginalFilename();
        File file = null;
        try {
            String fileName = fullFileName.substring(0, fullFileName.indexOf('.'));
            String extension = fullFileName.substring(fullFileName.indexOf('.') + 1);
            file = File.createTempFile(fileName, extension);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error("Error while trying to get file from multiPartFile", e);
        }
        return file;
    }
}