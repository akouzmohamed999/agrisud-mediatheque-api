package org.agrisud.mediathequeapi.service;

import org.agrisud.mediathequeapi.dao.ListThematicSupportDao;
import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListThematicSupportService {
	@Autowired
	ListThematicSupportDao listThematicSupportDao;
	public void addListThematicSupport(ListThematicSupport listThematicSupport) {
		listThematicSupportDao.addListThematicSupport(listThematicSupport);
	}

}
