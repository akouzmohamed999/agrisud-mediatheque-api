package org.agrisud.mediathequeapi.service;

import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.dao.CountryDao;
import org.agrisud.mediathequeapi.dao.ThematicDao;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.model.Thematic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
//	@Autowired
//	CountryDao countryDao;
//	@Autowired
//    EventCloudService eventCloudService;
//	
//	public int addCountry(Country country) {
//		country.setUrlImage(eventCloudService.doShared(country.getPathImage()) + "/preview");
//		return countryDao.addCountry(country);
//	}
//
//	public int deleteCountry(Long id) {
//		Optional<Category> category = getCategoryById(id);
//		//eventCloudService.deleteFile(null);
//		return 0;
//	}
//
//	public int updateCountry(Country country) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public List<Thematic> getAllCountry() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
