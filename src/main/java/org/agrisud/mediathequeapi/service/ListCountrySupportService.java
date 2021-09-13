package org.agrisud.mediathequeapi.service;

import org.agrisud.mediathequeapi.dao.ListCountrySupportDao;
import org.agrisud.mediathequeapi.model.ListCountrySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListCountrySupportService {
	@Autowired
	ListCountrySupportDao listCountrySupportDao; 
	
	public void addListCountrySupport(ListCountrySupport listCountrySupport) {
		listCountrySupportDao.addListCountrySupport(listCountrySupport);
	}

}
