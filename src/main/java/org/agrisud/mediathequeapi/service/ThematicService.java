package org.agrisud.mediathequeapi.service;

import java.util.List;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.dao.ThematicDao;
import org.agrisud.mediathequeapi.model.Thematic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThematicService {
	@Autowired
	ThematicDao thematicDao;
	@Autowired
	EventCloudDao eventCloudDao;
	
	public int addThematic(Thematic thematic) {
		thematic.setUrlImage(eventCloudDao.doShared(thematic.getPathImage()));
		return thematicDao.addThematic(thematic);
	}


	public int deleteThematic(Long id) {
		return thematicDao.deleteThematic(id);
	}


	public int updateThematic(Thematic thematic) {
		Thematic thematicOld = thematicDao.getThematicById(thematic.getThematicId());
		if(!thematicOld.getPathImage().equals(thematic.getPathImage())) {
			eventCloudDao.deleteFile(thematicOld.getPathImage());
			thematic.setUrlImage(eventCloudDao.doShared(thematic.getPathImage()));
		}
		return thematicDao.updateThematic(thematic);
	}


	public List<Thematic> getAllThematic() {
		return thematicDao.getAllThematic();
	}


	public Thematic getThematicById(Long id) {
		return thematicDao.getThematicById(id);
	}

	public List<Thematic> getListThematicByExpositionId(Long expositionId) {
		return thematicDao.getListThematicByExpositionId(expositionId);
	}
}
