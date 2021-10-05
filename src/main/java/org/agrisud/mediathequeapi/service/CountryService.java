package org.agrisud.mediathequeapi.service;

import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.dao.CountryDao;
import org.agrisud.mediathequeapi.dao.CountryDao;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
	@Autowired
	CountryDao countryDao;
	@Autowired
	EventCloudDao eventCloudDao;
	
	public int addCountry(Country country) {
		country.setUrlImage(eventCloudDao.doShared(country.getPathImage()));
		return countryDao.addCountry(country);
	}


	public int deleteCountry(Long id) {
		return countryDao.deleteCountry(id);
	}


	public int updateCountry(Country country) {
		Country countryOld = countryDao.getCountryById(country.getCountryId());
		if(!countryOld.getPathImage().equals(country.getPathImage())) {
			eventCloudDao.deleteFile(countryOld.getPathImage());
			country.setUrlImage(eventCloudDao.doShared(country.getPathImage()));
		}
		return countryDao.updateCountry(country);
	}


	public List<Country> getAllCountry() {
		return countryDao.getAllCountry();
	}


	public Country getCountryById(Long id) {
		return countryDao.getCountryById(id);
	}
}