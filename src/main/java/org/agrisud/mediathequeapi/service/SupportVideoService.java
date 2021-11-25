package org.agrisud.mediathequeapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.dao.CountryDao;
import org.agrisud.mediathequeapi.dao.ListCountrySupportVideoDao;
import org.agrisud.mediathequeapi.dao.ListThematicSupportVideoDao;
import org.agrisud.mediathequeapi.dao.StatisticMediaDao;
import org.agrisud.mediathequeapi.dao.SupportVideoDao;
import org.agrisud.mediathequeapi.dao.ThematicDao;
import org.agrisud.mediathequeapi.dao.VideoTypeDao;
import org.agrisud.mediathequeapi.enums.SortColumn;
import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.model.ListCountrySupport;
import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.agrisud.mediathequeapi.model.StatiscticCountView;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.model.SupportVideo;
import org.agrisud.mediathequeapi.model.Thematic;
import org.agrisud.mediathequeapi.search.SupportVideoSearchQueries;
import org.agrisud.mediathequeapi.search.SupportVideoSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
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
	@Autowired
	SupportVideoSearchRepository supportVideoSearchRepository;
	@Autowired
	SupportVideoSearchQueries supportVideoSearchQueries;
	@Autowired
	StatisticMediaDao statisticMediaDao;
	
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
		support.setSupportId(supportId);
		supportVideoSearchRepository.save(support);
		return supportId;
	}
	
	public SearchPage<SupportVideo> getSupportVideoBySearchCriteria(Map<String, Object> searchParams) {
		Sort sort = Sort.by("ASC" .equals(searchParams.get("sortDirection")) ? Sort.Direction.ASC : Sort.Direction.DESC, (String) searchParams.get("sortColumn"));
	    PageRequest pageRequest = PageRequest.of(Integer.parseInt((String) searchParams.get("page")), Integer.parseInt((String) searchParams.get("size")), sort);
	    String title = Optional.ofNullable((String) searchParams.get("title")).orElse(null);
	    String countryId = Optional.ofNullable((String) searchParams.get("countryId")).orElse(null);
	    String videoTypeId = Optional.ofNullable((String) searchParams.get("videoTypeId")).orElse(null);
	    String thematicId = Optional.ofNullable((String) searchParams.get("thematicId")).orElse(null);
	    String language = Optional.ofNullable((String) searchParams.get("language")).orElse(null);
	    String dateSupport = Optional.ofNullable((String) searchParams.get("dateSupport")).orElse(null);
	    Long categoryId = Optional.ofNullable((String) searchParams.get("categoryId")).map(Long::parseLong).orElse(null);
	    SearchPage<SupportVideo> listSupport = supportVideoSearchQueries.advancedSearch(title, countryId, videoTypeId, thematicId, language, dateSupport,categoryId, pageRequest);
	    listSupport.forEach(support -> {
	    	Optional<StatiscticCountView> statisticCountView = statisticMediaDao.getCountStatisticBymediaId(support.getContent().getCategoryId(),support.getContent().getSupportId());
	    	if(statisticCountView.isPresent() && !statisticCountView.isEmpty()) {
	    		support.getContent().setNumberDownload(statisticCountView.get().getNumberDownload());
	    		support.getContent().setNumberView(statisticCountView.get().getNumberView());
	    	}else {
	    		support.getContent().setNumberDownload(0);
	    		support.getContent().setNumberView(0);
	    	}
	    });
	    for(SearchHit<SupportVideo> support : listSupport.getContent()) {
	    	support.getContent().setVideoType(videoTypeDao.getVideoTypeById(support.getContent().getVideoTypeId()));
	    }
	    return listSupport ;
    }

	public void deleteSupportVideo(Long id) {
		SupportVideo support = supportVideoDao.getSupportVideoById(id);
		if(support.getPathSupport()!= null && support.getPathSupport() != "") {
			eventCloudDao.deleteFile(support.getPathSupport());
		}
		listThematicSupportVideoDao.deleteListThematicBySupportVideoId(support.getSupportId());
		listCountrySupportVideoDao.deleteListCounrtyBySupportVideoId(support.getSupportId());
		supportVideoDao.deleteSupportVideo(id);
		supportVideoSearchRepository.delete(supportVideoSearchRepository.findOneBySupportId(id));
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
		supportVideoSearchRepository.save(support);
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
	
	public SupportVideo getSupportVideoById(Long supportId) {
		List<Thematic> list = new ArrayList<Thematic>();
		List<Country> listPays = new ArrayList<Country>();
		SupportVideo supportVideo = supportVideoDao.getSupportVideoById(supportId);
		if(supportVideo != null) {
			for(ListCountrySupport listCountry : listCountrySupportVideoDao.getListCountryBySupportVideoId(supportVideo.getSupportId())) {
				listPays.add(countryDao.getCountryById(listCountry.getCountryId()));
			}
			for(ListThematicSupport listThematic : listThematicSupportVideoDao.getListThematicBySupportVideoId(supportVideo.getSupportId())) {
				list.add(thematicDao.getThematicById(listThematic.getThematicId()));
			}
			supportVideo.setListCountry(listPays);
			supportVideo.setListThematic(list);
			supportVideo.setVideoType(videoTypeDao.getVideoTypeById(supportVideo.getVideoTypeId()));
			return supportVideo;
		}else {
			return null;
		}
	}

	public SupportVideo getLastNews() {
		return supportVideoDao.getLastNews();
	}

}
