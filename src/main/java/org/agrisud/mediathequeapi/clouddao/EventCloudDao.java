package org.agrisud.mediathequeapi.clouddao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.aarboard.nextcloud.api.NextcloudConnector;
import org.aarboard.nextcloud.api.filesharing.Share;
import org.aarboard.nextcloud.api.filesharing.SharePermissions;
import org.aarboard.nextcloud.api.filesharing.SharePermissions.SingleRight;
import org.aarboard.nextcloud.api.filesharing.ShareType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EventCloudDao {

    @Autowired
    NextcloudConnector connector;
    

    public List<String> getEventFolders() {
        return connector.listFolderContent("/category");
    }

	public void createFolder(String path) {
		connector.createFolder(path);
	}
	
	public InputStream getFile(String path) throws IOException {
		return connector.downloadFile( path);
	}
	
	public void upLoadFile(File file, String path) {
        connector.uploadFile(file, path);
    }

	public Boolean isFolderExist(String path) {
		return connector.folderExists(path);
	}

	public void deleteFolder(String pathFolder) {
		connector.deleteFolder(pathFolder);
	}

	public void deleteFile(String pathFile) {
		connector.removeFile(pathFile);
	}

	public String doShared(String path) {
		SharePermissions permissions = new SharePermissions(SingleRight.READ);
		Share share = connector.doShare(path, ShareType.PUBLIC_LINK, null, false, null, permissions);
		return share.getUrl();
	}
	public void renameFile(String oldPath, String newPath) {
		connector.renameFile(oldPath, newPath, false);
	}
}
