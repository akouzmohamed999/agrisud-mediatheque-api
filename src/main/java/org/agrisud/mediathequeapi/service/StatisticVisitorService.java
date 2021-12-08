package org.agrisud.mediathequeapi.service;

import java.util.List;

import org.agrisud.mediathequeapi.dao.StatisticVisitorDao;
import org.agrisud.mediathequeapi.model.GroupedStatistic;
import org.agrisud.mediathequeapi.model.StatisticVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticVisitorService {
	@Autowired
	StatisticVisitorDao statisticVisitorDao;
	
	public void addStatisticVisitor(StatisticVisitor statisticVisitor) {
		 statisticVisitorDao.addStatisticVisitor(statisticVisitor);
	}

	public List<GroupedStatistic> getStatistic(String year, String groupBy) {
		if("month".equals(groupBy)) {
			return statisticVisitorDao.getStatisticGroupByMounth(year);
		}else {
			return statisticVisitorDao.getStatisticGroupByYear();
		}
	}

}
