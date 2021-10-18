package org.agrisud.mediathequeapi.service;

import java.util.List;

import org.agrisud.mediathequeapi.dao.ListCountrySupportDao;
import org.agrisud.mediathequeapi.dao.ListCountrySupportVideoDao;
import org.agrisud.mediathequeapi.model.ListCountrySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListCountrySupportVideoService {
	@Autowired
	ListCountrySupportVideoDao listCountrySupportVideoDao;

	public void addListCountrySupportVideo(ListCountrySupport listCountrySupport) {
		listCountrySupportVideoDao.addListCountrySupportVideo(listCountrySupport);
	}

	public List<ListCountrySupport> getListCountryBySupportVideoId(Long supportId) {
		return listCountrySupportVideoDao.getListCountryBySupportVideoId(supportId);
	}

}
