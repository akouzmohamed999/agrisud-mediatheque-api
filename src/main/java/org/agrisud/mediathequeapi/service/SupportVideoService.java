package org.agrisud.mediathequeapi.service;

import java.util.ArrayList;
import java.util.List;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.dao.CountryDao;
import org.agrisud.mediathequeapi.dao.ListCountrySupportVideoDao;
import org.agrisud.mediathequeapi.dao.ListThematicSupportDao;
import org.agrisud.mediathequeapi.dao.ListThematicSupportVideoDao;
import org.agrisud.mediathequeapi.dao.SupportVideoDao;
import org.agrisud.mediathequeapi.dao.ThematicDao;
import org.agrisud.mediathequeapi.dao.VideoTypeDao;
import org.agrisud.mediathequeapi.enums.SortColumn;
import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.model.ListCountrySupport;
import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.model.SupportVideo;
import org.agrisud.mediathequeapi.model.Thematic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupportVideoService {
	@Autowired
	ListCountrySupportVideoDao listCountrySupportVideoDao;
	@Autowired
	ListThematicSupportVideoDao listThematicSupportVideoDao;
	@Autowired
	SupportVideoDao supportVideoDao;
	@Autowired
	EventCloudDao eventCloudDao;
	@Autowired
	ThematicDao thematicDao;
	@Autowired
	VideoTypeDao videoTypeDao;
	@Autowired
	CountryDao countryDao;
	
	public Long addSupportVideo(SupportVideo support) {
		ListCountrySupport listCountrySupport = new ListCountrySupport();
		ListThematicSupport listThematicSupport = new ListThematicSupport();
		if(support.getPathSupport()!= null)
		support.setUrlSupport(eventCloudDao.doShared(support.getPathSupport()));
		Long supportId =  supportVideoDao.addSupport(support);
		
		listCountrySupport.setSupportId(supportId);
		listThematicSupport.setSupportId(supportId);
		
		
		for(Country country : support.getListCountry()) {
			listCountrySupport.setCountryId(country.getCountryId());
			listCountrySupportVideoDao.addListCountrySupportVideo(listCountrySupport);
		}
		for(Thematic thematic : support.getListThematic()) {
			listThematicSupport.setThematicId(thematic.getThematicId());
			listThematicSupportVideoDao.addListThematicSupportVideo(listThematicSupport);
		}
		return supportId;
	}
	
	public void deleteSupportVideo(Long id) {
		SupportVideo support = supportVideoDao.getSupportVideoById(id);
		if(support.getPathSupport()!= null && support.getPathSupport() != "") {
			eventCloudDao.deleteFile(support.getPathSupport());
		}
		listThematicSupportVideoDao.deleteListThematicBySupportVideoId(support.getSupportId());
		listCountrySupportVideoDao.deleteListCounrtyBySupportVideoId(support.getSupportId());
		supportVideoDao.deleteSupportVideo(id);
	}

	public List<SupportVideo> getListSupportVideo(Long categoryId) {
		List<Thematic> list = new ArrayList<Thematic>();
		List<Country> listPays = new ArrayList<Country>();
		List<SupportVideo> listSupport = supportVideoDao.getListSupportVideo(categoryId);
		for(SupportVideo support : listSupport) {
			list = new ArrayList<>();
			listPays = new ArrayList<>();
			for(ListCountrySupport listCountry : listCountrySupportVideoDao.getListCountryBySupportVideoId(support.getSupportId())) {
				listPays.add(countryDao.getCountryById(listCountry.getCountryId()));
			}
			for(ListThematicSupport listThematic : listThematicSupportVideoDao.getListThematicBySupportVideoId(support.getSupportId())) {
				list.add(thematicDao.getThematicById(listThematic.getThematicId()));
			}
			support.setListCountry(listPays);
			support.setListThematic(list);
			support.setVideoType(videoTypeDao.getVideoTypeById(support.getVideoTypeId()));
		}
		
		return listSupport;
	}
	
	public void updateSupportVideo(SupportVideo support) {
		SupportVideo supportOld = supportVideoDao.getSupportVideoById(support.getSupportId());
		if(supportOld.getPathSupport()!= null && !"".equals(supportOld.getPathSupport())) {
			if(!supportOld.getPathSupport().equals(support.getPathSupport())) {
				eventCloudDao.deleteFile(supportOld.getPathSupport());
				if(!"".equals(support.getPathSupport()) && support.getPathSupport() !=null)
				support.setUrlSupport(eventCloudDao.doShared(support.getPathSupport()));
			}
		}else {
			if(support.getPathSupport()!=null && !"".equals(support.getPathSupport())) {
				support.setUrlSupport(eventCloudDao.doShared(support.getPathSupport()));
			}
		}
		
		listThematicSupportVideoDao.deleteListThematicBySupportVideoId(support.getSupportId());
		listCountrySupportVideoDao.deleteListCounrtyBySupportVideoId(support.getSupportId());
		for(Thematic thematic : support.getListThematic()) {
			listThematicSupportVideoDao.addListThematicSupportVideo(
					ListThematicSupport.builder()
					.supportId(support.getSupportId())
					.thematicId(thematic.getThematicId())
					.build());
		}
		for(Country country : support.getListCountry()) {
			listCountrySupportVideoDao.addListCountrySupportVideo(
					ListCountrySupport.builder().supportId(support.getSupportId()).countryId(country.getCountryId()).build()
					);
		}
		supportVideoDao.updateSupportVideo(support);
	}

	public List<SupportVideo> getSupportVideoByOrder(Long categoryId, SortColumn sortColumn, Boolean asc,String language) {
		List<Thematic> list = new ArrayList<Thematic>();
		List<Country> listPays = new ArrayList<Country>();
		List<SupportVideo> listSupportVideo;
		if (asc) {
			listSupportVideo = supportVideoDao.getSupportVideoByOrderASC(categoryId, sortColumn,language);
        } else {
        	listSupportVideo =  supportVideoDao.getSupportVideoByOrderDESC(categoryId, sortColumn,language);
        }
		
		for(SupportVideo supportVideo : listSupportVideo) {
			list = new ArrayList<>();
			listPays = new ArrayList<>();
			for(ListCountrySupport listCountry : listCountrySupportVideoDao.getListCountryBySupportVideoId(supportVideo.getSupportId())) {
				listPays.add(countryDao.getCountryById(listCountry.getCountryId()));
			}
			for(ListThematicSupport listThematic : listThematicSupportVideoDao.getListThematicBySupportVideoId(supportVideo.getSupportId())) {
				list.add(thematicDao.getThematicById(listThematic.getThematicId()));
			}
			supportVideo.setListCountry(listPays);
			supportVideo.setListThematic(list);
			supportVideo.setVideoType(videoTypeDao.getVideoTypeById(supportVideo.getVideoTypeId()));
		}
		
		return listSupportVideo;
	}

}
