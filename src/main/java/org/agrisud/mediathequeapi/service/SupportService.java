package org.agrisud.mediathequeapi.service;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;
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


	public List<Support> getListSupport(Long categoryId) {
		List<Thematic> list = new ArrayList<Thematic>();
		List<Country> listPays = new ArrayList<Country>();
		List<Support> listSupport = supportDao.getListSupport(categoryId);
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
	

}
