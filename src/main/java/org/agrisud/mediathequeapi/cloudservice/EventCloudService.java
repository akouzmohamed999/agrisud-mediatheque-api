package org.agrisud.mediathequeapi.cloudservice;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventCloudService {

    @Autowired
    EventCloudDao eventCloudDao;

    public List<String> getFolders() {
        return eventCloudDao.getEventFolders();
    }
}
