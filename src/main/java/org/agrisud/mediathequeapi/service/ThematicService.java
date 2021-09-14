package org.agrisud.mediathequeapi.service;

import java.util.List;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.dao.ThematicDao;
import org.agrisud.mediathequeapi.model.Thematic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThematicService {
	@Autowired
	ThematicDao thematicDao;
	
	
	public int addThematic(Thematic thematic) {
		
		return thematicDao.addThematic(thematic);
	}


	public int deleteThematic(Long id) {
		return thematicDao.deleteThematic(id);
	}


	public int updateThematic(Thematic thematic) {
		return thematicDao.updateThematic(thematic);
	}


	public List<Thematic> getAllThematic() {
		return thematicDao.getAllThematic();
	}


	public Thematic getThematicById(Long id) {
		return thematicDao.getThematicById(id);
	}

}
