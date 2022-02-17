package org.agrisud.mediathequeapi.service;

import java.util.List;

import org.agrisud.mediathequeapi.dao.ListThematicSupportDao;
import org.agrisud.mediathequeapi.dao.ListThematicSupportVideoDao;
import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListThematicSupportVideoService {
    @Autowired
    ListThematicSupportVideoDao listThematicSupporVideotDao;
    public void addListThematicSupportVideo(ListThematicSupport listThematicSupport) {
        listThematicSupporVideotDao.addListThematicSupportVideo(listThematicSupport);
    }
    public List<ListThematicSupport> getListThematicBySupportVideoId(Long supportId) {
        return listThematicSupporVideotDao.getListThematicBySupportVideoId(supportId);
    }

}
