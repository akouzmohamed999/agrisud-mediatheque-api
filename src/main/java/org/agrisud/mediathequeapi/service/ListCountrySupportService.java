package org.agrisud.mediathequeapi.service;

import java.util.List;

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

	public List<String> getListCountryBySupportId(Long supportId) {
		return  listCountrySupportDao.getListCountryBySupportId(supportId);
	}

}
