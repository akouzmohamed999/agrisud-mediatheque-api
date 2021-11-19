package org.agrisud.mediathequeapi.service;

import java.util.List;

import org.agrisud.mediathequeapi.dao.CountryDao;
import org.agrisud.mediathequeapi.dao.ExpositionDao;
import org.agrisud.mediathequeapi.dao.ExpositionImageDao;
import org.agrisud.mediathequeapi.dao.SupportDao;
import org.agrisud.mediathequeapi.dao.SupportVideoDao;
import org.agrisud.mediathequeapi.dao.ThematicDao;
import org.agrisud.mediathequeapi.dao.ThematicFolderMediaDao;
import org.agrisud.mediathequeapi.model.Exposition;
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
	@Autowired
	ThematicDao thematicDao;
	@Autowired
	CountryDao countryDao;
	@Autowired
	ExpositionImageDao expositionImageDao;
	
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
				Exposition exposition = expositionDao.getExpositionById(media.getMediaId());
		    	if(exposition != null) {
		    		exposition.setListThematic(thematicDao.getListThematicByExpositionId(exposition.getExpositionId()));
		        	exposition.setListCountry(List.of( countryDao.getCountryById(exposition.getCountryId())));
		        	exposition.setListExpositionImage(expositionImageDao.getListExpositionImageByExpositionId(exposition.getExpositionId()));
		    	}
				media.setExposition(exposition);
			}
		});
		return listMedia;
	}

	public void deleteThematicFolderById(Long thematicFolderMediaId) {
		thematicFolderDao.deleteThematicFolderById(thematicFolderMediaId);
	}

}
