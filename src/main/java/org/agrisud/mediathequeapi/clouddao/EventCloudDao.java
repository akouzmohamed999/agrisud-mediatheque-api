package org.agrisud.mediathequeapi.clouddao;

import org.aarboard.nextcloud.api.NextcloudConnector;
import org.aarboard.nextcloud.api.filesharing.Share;
import org.aarboard.nextcloud.api.filesharing.SharePermissions;
import org.aarboard.nextcloud.api.filesharing.SharePermissions.SingleRight;
import org.aarboard.nextcloud.api.filesharing.ShareType;
import org.agrisud.mediathequeapi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

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
	
	@SuppressWarnings("deprecation")
	public void addImage(MultipartFile multipartFile,String path,String generatedKey) {
//		try (InputStream inputStream = multipartFile.getInputStream()) {
//			File convFile = new File(multipartFile.getOriginalFilename());
//			convFile.createNewFile();
//			String [] name = convFile.getPath().split("\\.");
//			connector.uploadFile(inputStream, "/img/"+ name[0] + "_" + generatedKey + "." + name[1]);
//			System.out.println("log");
//		}catch (IOException e) {
//			System.out.println(e.getMessage());
//		}
		

		
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
		Share share = connector.doShare("/selector.mp4", ShareType.PUBLIC_LINK, null, false, null, permissions);
		return share.getUrl();
	}


}
