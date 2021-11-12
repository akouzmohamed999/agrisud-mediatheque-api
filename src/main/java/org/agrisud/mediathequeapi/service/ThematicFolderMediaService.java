package org.agrisud.mediathequeapi.service;

import java.util.List;

import org.agrisud.mediathequeapi.dao.ExpositionDao;
import org.agrisud.mediathequeapi.dao.SupportDao;
import org.agrisud.mediathequeapi.dao.SupportVideoDao;
import org.agrisud.mediathequeapi.dao.ThematicFolderMediaDao;
import org.agrisud.mediathequeapi.model.ThematicFolderMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThematicFolderMediaService {
	@Autowired
	ThematicFolderMediaDao thematicFolderDao;
	@Autowired
	SupportDao supportDao;
	@Autowired
	SupportVideoDao supportVideoDao;
	@Autowired
	ExpositionDao expositionDao;
	
	public void addThematicFolderMedia(List<ThematicFolderMedia> listThematicFolderMedia) {
		listThematicFolderMedia.forEach(media -> {
			thematicFolderDao.addThematicFolderMedia(media);
		});
		
	}

	public List<ThematicFolderMedia> getThematicFolderMediaByParentId(Long parentId) {
		List<ThematicFolderMedia> listMedia =  thematicFolderDao.getThematicFolderMediaByParentId(parentId);
		listMedia.forEach(media -> {
			if(Integer.parseInt(media.getTypeCategory()) == 0) {
				media.setSupport(supportDao.getSupportById(media.getMediaId()).get());
			}else if(Integer.parseInt(media.getTypeCategory()) == 1) {
				media.setSupportVideo(supportVideoDao.getSupportVideoById(media.getMediaId()));
			}else {
				media.setExposition(expositionDao.getExpositionById(media.getMediaId()));
			}
		});
		return listMedia;
	}

}
