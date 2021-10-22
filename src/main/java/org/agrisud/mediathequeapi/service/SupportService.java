package org.agrisud.mediathequeapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.dao.CountryDao;
import org.agrisud.mediathequeapi.dao.DocumentTypeDao;
import org.agrisud.mediathequeapi.dao.ListCountrySupportDao;
import org.agrisud.mediathequeapi.dao.ListThematicSupportDao;
import org.agrisud.mediathequeapi.dao.SupportDao;
import org.agrisud.mediathequeapi.dao.ThematicDao;
import org.agrisud.mediathequeapi.enums.SortColumn;
import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.model.ListCountrySupport;
import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.model.Thematic;
import org.agrisud.mediathequeapi.search.SupportSearchQueries;
import org.agrisud.mediathequeapi.search.SupportSearchRepository;
import org.agrisud.mediathequeapi.search.SupportSearchQueries;
import org.agrisud.mediathequeapi.search.SupportSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Service
public class SupportService {
	@Autowired
	ListCountrySupportDao listCountrySupportDao;
	@Autowired
	ListThematicSupportDao listThematicSupportDao;
	@Autowired
	SupportDao supportDao;
	@Autowired
	EventCloudDao eventCloudDao;
	@Autowired
	ThematicDao thematicDao;
	@Autowired
	DocumentTypeDao documentTypeDao;
	@Autowired
	CountryDao countryDao;
	@Autowired
	SupportSearchRepository supportSearchRepository;
	@Autowired
	SupportSearchQueries supportSearchQueries;
	
	
	public Long addSupport(Support support) {
		ListCountrySupport listCountrySupport = new ListCountrySupport();
		ListThematicSupport listThematicSupport = new ListThematicSupport();
		if(support.getPathImage() != null ) {
			support.setUrlImage(eventCloudDao.doShared(support.getPathImage())+ "/preview");
		}
		support.setUrlSupport(eventCloudDao.doShared(support.getPathSupport()));
		Long supportId =  supportDao.addSupport(support);
		
		listCountrySupport.setSupportId(supportId);
		listThematicSupport.setSupportId(supportId);
		
		
		for(Country country : support.getListCountry()) {
			listCountrySupport.setCountryId(country.getCountryId());
			listCountrySupportDao.addListCountrySupport(listCountrySupport);
		}
		for(Thematic thematic : support.getListThematic()) {
			listThematicSupport.setThematicId(thematic.getThematicId());
			listThematicSupportDao.addListThematicSupport(listThematicSupport);
		}
		return supportId;
	}

    public SearchPage<Support> getSupportBySearchCriteria(Map<String, Object> searchParams) {
        Sort sort = Sort.by("ASC" .equals(searchParams.get("sortDirection")) ? Sort.Direction.ASC : Sort.Direction.DESC, (String) searchParams.get("sortColumn"));
        PageRequest pageRequest = PageRequest.of(Integer.parseInt((String) searchParams.get("page")), Integer.parseInt((String) searchParams.get("size")), sort);

        return supportSearchQueries.advancedSearch((String) searchParams.get("title"), Long.parseLong((String) searchParams.get("countryId")), Long.parseLong((String) searchParams.get("documentTypeId")),
                Long.parseLong((String) searchParams.get("thematicId")), (String) searchParams.get("language"), (String) searchParams.get("dateSupport"), pageRequest);
    }


	public Page<Support> getListSupport(Long categoryId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
		List<Thematic> list = new ArrayList<Thematic>();
		List<Country> listPays = new ArrayList<Country>();
		Page<Support> listSupport = supportDao.getListSupport(categoryId,pageRequest);
		List<ListCountrySupport> listPaysd = new ArrayList<ListCountrySupport>();
		for(Support support : listSupport) {
			list = new ArrayList<>();
			listPays = new ArrayList<>();
			for(ListCountrySupport listCountry: listCountrySupportDao.getListCountryBySupportId(support.getSupportId())) {
				listPays.add(countryDao.getCountryById(listCountry.getCountryId()));
			}
			for(ListThematicSupport listThematic : listThematicSupportDao.getListThematicBySupportId(support.getSupportId())) {
				list.add(thematicDao.getThematicById(listThematic.getThematicId()));
			}
			support.setListThematic(list);
			support.setListCountry(listPays);
			support.setDocumentType(documentTypeDao.getDocumentTypeById(support.getDocumentTypeId()));
		}
		
		return listSupport;
	}


	public void deleteSupport(Long id) {
		Support support = supportDao.getSupportById(id);
		if(support.getPathImage()!= null && !"".equals(support.getPathImage())) {
			eventCloudDao.deleteFile(support.getPathImage());
		}
		if(support.getPathSupport()!= null && support.getPathSupport() != "") {
			eventCloudDao.deleteFile(support.getPathSupport());
		}
		listThematicSupportDao.deleteListThematicBySupportId(support.getSupportId());
		listCountrySupportDao.deleteListCounrtyBySupportId(support.getSupportId());
		supportDao.deleteSupport(id);
	}
	
	public void updateSupport(Support support) {
		Support supportOld = supportDao.getSupportById(support.getSupportId());
		if(!supportOld.getPathSupport().equals(support.getPathSupport())) {
			eventCloudDao.deleteFile(supportOld.getPathSupport());
			support.setUrlSupport(eventCloudDao.doShared(support.getPathSupport()));
		}
		if(supportOld.getPathImage()!=null) {
			if(!supportOld.getPathImage().equals(support.getPathImage())) {
				eventCloudDao.deleteFile(supportOld.getPathImage());
				support.setUrlImage(eventCloudDao.doShared(support.getPathImage()) + "/preview");
			}
		}else {
			if(support.getPathImage() != null && !"".equals(support.getPathImage())) {
				support.setUrlImage(eventCloudDao.doShared(support.getPathImage()) + "/preview");
			}
		}
		listThematicSupportDao.deleteListThematicBySupportId(support.getSupportId());
		listCountrySupportDao.deleteListCounrtyBySupportId(support.getSupportId());
		for(Thematic thematic : support.getListThematic()) {
			listThematicSupportDao.addListThematicSupport(
					ListThematicSupport.builder()
					.supportId(support.getSupportId())
					.thematicId(thematic.getThematicId())
					.build());
		}
		for(Country country : support.getListCountry()) {
			listCountrySupportDao.addListCountrySupport(
					ListCountrySupport.builder().supportId(support.getSupportId()).countryId(country.getCountryId()).build()
					);
		}
		supportDao.updateSupport(support);
	}


	public List<Support> getSupportByOrder(Long categoryId,SortColumn sortColumn, Boolean asc, String language) {
		List<Thematic> list = new ArrayList<Thematic>();
		List<Country> listPays = new ArrayList<Country>();
		List<Support> listSupport;
		if (asc) {
			listSupport = supportDao.getSupportByOrderASC(categoryId, sortColumn,language);
        } else {
        	listSupport =  supportDao.getSupportByOrderDESC(categoryId, sortColumn,language);
        }
		
		for(Support support : listSupport) {
			list = new ArrayList<>();
			listPays = new ArrayList<>();
			for(ListCountrySupport listCountry: listCountrySupportDao.getListCountryBySupportId(support.getSupportId())) {
				listPays.add(countryDao.getCountryById(listCountry.getCountryId()));
			}
			for(ListThematicSupport listThematic : listThematicSupportDao.getListThematicBySupportId(support.getSupportId())) {
				list.add(thematicDao.getThematicById(listThematic.getThematicId()));
			}
			support.setListCountry(listPays);
			support.setListThematic(list);
			support.setDocumentType(documentTypeDao.getDocumentTypeById(support.getDocumentTypeId()));
		}
		
		return listSupport;
	}


	public Support getSupportById(Long supportId) {
		Support support = supportDao.getSupportById(supportId);
		List<Thematic> list = new ArrayList<Thematic>();
		List<Country> listPays = new ArrayList<Country>();
		for(ListCountrySupport listCountry: listCountrySupportDao.getListCountryBySupportId(support.getSupportId())) {
			listPays.add(countryDao.getCountryById(listCountry.getCountryId()));
		}
		for(ListThematicSupport listThematic : listThematicSupportDao.getListThematicBySupportId(support.getSupportId())) {
			list.add(thematicDao.getThematicById(listThematic.getThematicId()));
		}
		support.setListCountry(listPays);
		support.setListThematic(list);
		support.setDocumentType(documentTypeDao.getDocumentTypeById(support.getDocumentTypeId()));
		return support;

	}


	public Support getLastNews() {
		return supportDao.getLastNews();

	}


}
