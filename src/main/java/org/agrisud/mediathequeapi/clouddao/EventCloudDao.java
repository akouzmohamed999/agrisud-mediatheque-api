package org.agrisud.mediathequeapi.clouddao;

import org.aarboard.nextcloud.api.NextcloudConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventCloudDao {

    @Autowired
    NextcloudConnector connector;

    public List<String> getEventFolders() {
        return connector.getFolders("/");
    }

}
