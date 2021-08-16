package org.agrisud.mediathequeapi.clouddao;

import lombok.extern.slf4j.Slf4j;
import org.aarboard.nextcloud.api.NextcloudConnector;
import org.agrisud.mediathequeapi.util.CloudFileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class EventCloudDao {

    @Autowired
    NextcloudConnector connector;

    public List<String> getEventFolders() {
        return connector.listFolderContent("/");
    }

    public void upLoadFile(File file, String path) {
        connector.uploadFile(file, path);
    }

}
