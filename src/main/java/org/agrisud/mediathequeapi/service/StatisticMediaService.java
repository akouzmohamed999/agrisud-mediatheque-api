package org.agrisud.mediathequeapi.service;

import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.dao.StatisticMediaDao;
import org.agrisud.mediathequeapi.model.GroupedStatistic;
import org.agrisud.mediathequeapi.model.StatisticData;
import org.agrisud.mediathequeapi.model.StatisticMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticMediaService {
	@Autowired
	StatisticMediaDao statisticMediaDao;
	
	public void addStatisticMedia(StatisticMedia statisticMedia) {
		 statisticMediaDao.addStatisticMedia(statisticMedia);
	}

	public Optional<StatisticData> getPercentage() {
		Optional<StatisticData> statisticData = statisticMediaDao.getPercentage();
		statisticData.get().setTotal(statisticData.get().getNumberDocument()+ statisticData.get().getNumberVideo() + statisticData.get().getNumberExposition());
		return statisticData;
	}

	public List<GroupedStatistic> getStatistic(String year, String action, String typeCategory, String groupBy) {
		if("month".equals(groupBy)) {
			return statisticMediaDao.getStatisticGroupByMounth(year,action,typeCategory);
		}else {
			return statisticMediaDao.getStatisticGroupByYear(action,typeCategory);
		}
	}


}
