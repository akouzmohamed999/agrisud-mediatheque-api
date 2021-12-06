package org.agrisud.mediathequeapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.dao.CountryDao;
import org.agrisud.mediathequeapi.dao.ExpositionDao;
import org.agrisud.mediathequeapi.dao.ExpositionImageDao;
import org.agrisud.mediathequeapi.dao.ListCountrySupportDao;
import org.agrisud.mediathequeapi.dao.ListCountrySupportVideoDao;
import org.agrisud.mediathequeapi.dao.ListThematicSupportDao;
import org.agrisud.mediathequeapi.dao.ListThematicSupportVideoDao;
import org.agrisud.mediathequeapi.dao.StatisticMediaDao;
import org.agrisud.mediathequeapi.dao.SupportDao;
import org.agrisud.mediathequeapi.dao.SupportVideoDao;
import org.agrisud.mediathequeapi.dao.ThematicDao;
import org.agrisud.mediathequeapi.dao.ThematicFolderMediaDao;
import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.model.Exposition;
import org.agrisud.mediathequeapi.model.StatiscticCountView;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.model.SupportVideo;
import org.agrisud.mediathequeapi.model.Thematic;
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
	@Autowired
	StatisticMediaDao statisticMediaDao;
	@Autowired
	ListCountrySupportVideoDao listCountrySupportVideoDao;
	@Autowired
	ListCountrySupportDao listCountrySupportDao;
	@Autowired
	ListThematicSupportVideoDao listThematicSupportVideoDao;
	@Autowired
	ListThematicSupportDao listThematicSupportDao;
	
	public void addThematicFolderMedia(List<ThematicFolderMedia> listThematicFolderMedia) {
		listThematicFolderMedia.forEach(media -> {
			thematicFolderDao.addThematicFolderMedia(media);
		});
		
	}

	public List<ThematicFolderMedia> getThematicFolderMediaByParentId(Long parentId) {
		List<ThematicFolderMedia> listMedia =  thematicFolderDao.getThematicFolderMediaByParentId(parentId);
		listMedia.forEach(media -> {
			List<Country> listCountry = new ArrayList<Country>();
	    	List<Thematic> listThematic = new ArrayList<Thematic>();
			Optional<StatiscticCountView> statisticCountView = statisticMediaDao.getCountStatisticBymediaId(media.getCategoryId(),media.getMediaId());
			if(Integer.parseInt(media.getTypeCategory()) == 0) {
				Support support = supportDao.getSupportById(media.getMediaId()).get();
				
				listCountrySupportDao.getListCountryBySupportId(support.getSupportId()).forEach(country -> {
		    		listCountry.add(countryDao.getCountryById(country.getCountryId()));
		    	});
		    	support.setListCountry(listCountry);
		    	listThematicSupportDao.getListThematicBySupportId(support.getSupportId()).forEach(thematic ->{
		    		listThematic.add(thematicDao.getThematicById(thematic.getThematicId()));
		    	});
		    	support.setListThematic(listThematic);
				if(statisticCountView.isPresent() && !statisticCountView.isEmpty()) {
					support.setNumberDownload(statisticCountView.get().getNumberDownload());
					support.setNumberView(statisticCountView.get().getNumberView());
				}else {
					support.setNumberDownload(0);
					support.setNumberView(0);
				}
				
				media.setSupport(support);
			}else if(Integer.parseInt(media.getTypeCategory()) == 1) {
				SupportVideo supportVideo = supportVideoDao.getSupportVideoById(media.getMediaId());
				listCountrySupportVideoDao.getListCountryBySupportVideoId(supportVideo.getSupportId()).forEach(country -> {
		    		listCountry.add(countryDao.getCountryById(country.getCountryId()));
		    	});
				supportVideo.setListCountry(listCountry);
				listThematicSupportVideoDao.getListThematicBySupportVideoId(supportVideo.getSupportId()).forEach(thematic ->{
		    		listThematic.add(thematicDao.getThematicById(thematic.getThematicId()));
		    	});
				supportVideo.setListThematic(listThematic);
				if(statisticCountView.isPresent() && !statisticCountView.isEmpty()) {
					supportVideo.setNumberDownload(statisticCountView.get().getNumberDownload());
					supportVideo.setNumberView(statisticCountView.get().getNumberView());
				}else {
					supportVideo.setNumberDownload(0);
					supportVideo.setNumberView(0);
				}
				media.setSupportVideo(supportVideo);
			}else {
				Exposition exposition = expositionDao.getExpositionById(media.getMediaId());
				if(statisticCountView.isPresent() && !statisticCountView.isEmpty()) {
					exposition.setNumberDownload(statisticCountView.get().getNumberDownload());
					exposition.setNumberView(statisticCountView.get().getNumberView());
				}else {
					exposition.setNumberDownload(0);
					exposition.setNumberView(0);
				}
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
