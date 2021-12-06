package org.agrisud.mediathequeapi.service;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.dao.*;
import org.agrisud.mediathequeapi.model.*;
import org.agrisud.mediathequeapi.search.ExpositionSearchQueries;
import org.agrisud.mediathequeapi.search.ExpositionSearchRepository;
import org.agrisud.mediathequeapi.search.SupportSearchQueries;
import org.agrisud.mediathequeapi.search.SupportSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExpositionService {
    @Autowired
    ExpositionDao expositionDao;
    @Autowired
    ExpositionImageDao expositionImageDao;
    @Autowired
    ListExpositionImageDao listExpositionImageDao;
    @Autowired
    ListThematicExpositionDao listThematicExpositionDao;
    @Autowired
    ThematicDao thematicDao;
    @Autowired
    CountryDao countryDao;
    @Autowired
	ExpositionSearchRepository expositionSearchRepository;
	@Autowired
	ExpositionSearchQueries expositionSearchQueries;
	@Autowired
	EventCloudService eventCloudService;
	@Autowired
	StatisticMediaDao statisticMediaDao;

    public Long addExposition(Exposition exposition) {
        ListExpositionImage listExpositionImage = new ListExpositionImage();
        ListThematicExposition listThematicExposition = new ListThematicExposition();
        Long expositionId = expositionDao.addExposition(exposition);

        listExpositionImage.setExpositionId(expositionId);
        listThematicExposition.setExpositionId(expositionId);
        for (ExpositionImage expositionImage : exposition.getListExpositionImage()) {
            listExpositionImage.setExpositionImageId(expositionImage.getExpositionImageId());
            listExpositionImageDao.addListExpositionImage(listExpositionImage);
        }
        for (Thematic thematic : exposition.getListThematic()) {
            listThematicExposition.setThematicId(thematic.getThematicId());
            listThematicExpositionDao.addListThematicExposition(listThematicExposition);
        }
        exposition.setExpositionId(expositionId);
        expositionSearchRepository.save(exposition);
        return expositionId;
    }

    public Long updateExposition(Exposition exposition) {
        ListExpositionImage listExpositionImage = new ListExpositionImage();
        ListThematicExposition listThematicExposition = new ListThematicExposition();
        Long expositionId = exposition.getExpositionId();

        if(expositionDao.updateExposition(exposition) == 1){
        	expositionSearchRepository.save(exposition);
            listExpositionImageDao.deleteListExpositionImageByExpositionId(exposition.getExpositionId());
            listThematicExpositionDao.deleteListThematicByExpositionId(exposition.getExpositionId());

            listExpositionImage.setExpositionId(expositionId);
            listThematicExposition.setExpositionId(expositionId);
            for (ExpositionImage expositionImage : exposition.getListExpositionImage()) {
                listExpositionImage.setExpositionImageId(expositionImage.getExpositionImageId());
                listExpositionImageDao.addListExpositionImage(listExpositionImage);
            }
            for (Thematic thematic : exposition.getListThematic()) {
                listThematicExposition.setThematicId(thematic.getThematicId());
                listThematicExpositionDao.addListThematicExposition(listThematicExposition);
            }
        }
        return expositionId;
    }

    public int deleteExposition(Long id) {
    	for(ExpositionImage expositionImage : expositionImageDao.getListExpositionImageByExpositionId(id)) {
    		eventCloudService.deleteFile(expositionImage.getPathImage());
    	}
        expositionImageDao.deleteExpositionImageByIdExposition(id);
        listExpositionImageDao.deleteListExpositionImageByExpositionId(id);
        listThematicExpositionDao.deleteListThematicByExpositionId(id);
        expositionSearchRepository.delete(expositionSearchRepository.findOneByExpositionId(id));
        return expositionDao.deleteExposition(id);
    }

    public List<Exposition> getAllExpositionByCategory(Long idCategory) {
        List<Exposition> expositionList = expositionDao.getListExpositionByCategory(idCategory);
        expositionList.forEach(exposition -> {
            exposition.setListExpositionImage(expositionImageDao.getListExpositionImageByExpositionId(exposition.getExpositionId()));
            exposition.setListThematic(thematicDao.getListThematicByExpositionId(exposition.getExpositionId()));
            exposition.setListCountry(List.of( countryDao.getCountryById(exposition.getCountryId())));
        });
        return expositionList;
    }

    public Exposition getExpositionById(Long id) {
    	Exposition exposition = expositionDao.getExpositionById(id);
    	if(exposition != null) {
    		exposition.setListThematic(thematicDao.getListThematicByExpositionId(exposition.getExpositionId()));
        	exposition.setListCountry(List.of( countryDao.getCountryById(exposition.getCountryId())));
        	exposition.setListExpositionImage(expositionImageDao.getListExpositionImageByExpositionId(exposition.getExpositionId()));
            return exposition;
    	}else {
    		return null;
    	}
    	
    }

	public Exposition getLastNews() {
		return expositionDao.getLastNews();
	}

	public SearchPage<Exposition> getExpositionBySearchCriteria(Map<String, Object> searchParams) {
        Optional<String> sortDirection = Optional.ofNullable((String) searchParams.get("sortDirection"));
        Optional<String> sortColumn = Optional.ofNullable((String) searchParams.get("sortColumn"));
        Optional<Sort> sort = Optional.empty();
        if(sortDirection.isPresent() && sortColumn.isPresent()){
            sort = Optional.of(Sort.by("ASC".equals(searchParams.get("sortDirection")) ? Sort.Direction.ASC : Sort.Direction.DESC, (String) searchParams.get("sortColumn")));
        }
        PageRequest pageRequest = sort.map(orders -> PageRequest.of(Integer.parseInt((String) searchParams.get("page")), Integer.parseInt((String) searchParams.get("size")), orders))
                .orElseGet(() -> PageRequest.of(Integer.parseInt((String) searchParams.get("page")), Integer.parseInt((String) searchParams.get("size"))));
	    String titleFr = Optional.ofNullable((String) searchParams.get("titleFr")).orElse(null);
	    String titleEn = Optional.ofNullable((String) searchParams.get("titleEn")).orElse(null);
	    String countryId = Optional.ofNullable((String) searchParams.get("countryId")).orElse(null);
	    String thematicId = Optional.ofNullable((String) searchParams.get("thematicId")).orElse(null);
	    String dateSupport = Optional.ofNullable((String) searchParams.get("dateSupport")).orElse(null);
	    Long categoryId = Optional.ofNullable((String) searchParams.get("categoryId")).map(Long::parseLong).orElse(null);
	    SearchPage<Exposition> listExposition = expositionSearchQueries.advancedSearch(titleFr,titleEn, countryId, thematicId, dateSupport,categoryId, pageRequest);
	    listExposition.forEach(exposition -> {
	    	List<Country> listCountry = new ArrayList<Country>();
	    	List<Thematic> listThematic = new ArrayList<Thematic>();
	    	exposition.getContent().getListThematic().forEach(thematic ->{
	    		listThematic.add(thematicDao.getThematicById(thematic.getThematicId()));
	    	});
	    	exposition.getContent().setListThematic(listThematic);

	    	Optional<StatiscticCountView> statisticCountView = statisticMediaDao.getCountStatisticBymediaId(exposition.getContent().getCategoryId(),exposition.getContent().getExpositionId());
	    	if(statisticCountView.isPresent() && !statisticCountView.isEmpty()) {
	    		exposition.getContent().setNumberDownload(statisticCountView.get().getNumberDownload());
	    		exposition.getContent().setNumberView(statisticCountView.get().getNumberView());
	    	}else {
	    		exposition.getContent().setNumberDownload(0);
	    		exposition.getContent().setNumberView(0);
	    	}
	    });
	    for(SearchHit<Exposition> exposition: listExposition.getContent()) {
	    	exposition.getContent().setListCountry(List.of(countryDao.getCountryById(exposition.getContent().getCountryId())));
	    }
		return listExposition;
	}
}
