package org.agrisud.mediathequeapi.service;

import java.util.ArrayList;
import java.util.List;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.dao.DocumentTypeDao;
import org.agrisud.mediathequeapi.dao.ListCountrySupportDao;
import org.agrisud.mediathequeapi.dao.ListThematicSupportDao;
import org.agrisud.mediathequeapi.dao.SupportDao;
import org.agrisud.mediathequeapi.dao.ThematicDao;
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
		
		
		for(String country : support.getListCountry()) {
			listCountrySupport.setCountryCode(country);
			listCountrySupportDao.addListCountrySupport(listCountrySupport);
		}
		for(Thematic thematic : support.getListThematic()) {
			listThematicSupport.setThematicId(thematic.getThematicId());
			listThematicSupportDao.addListThematicSupport(listThematicSupport);
		}
		return supportId;
	}


	public List<Support> getListSupport() {
		List<Thematic> list = new ArrayList<Thematic>();
		List<Support> listSupport = supportDao.getListSupport();
		for(Support support : listSupport) {
			list = new ArrayList<>();
			support.setListCountry(listCountrySupportDao.getListCountryBySupportId(support.getSupportId()));
			for(ListThematicSupport listThematic : listThematicSupportDao.getListThematicBySupportId(support.getSupportId())) {
				list.add(thematicDao.getThematicById(listThematic.getThematicId()));
			}
			support.setListThematic(list);
			support.setDocumentType(documentTypeDao.getDocumentTypeById(support.getDocumentTypeId()));
		}
		
		return listSupport;
	}
	

}
