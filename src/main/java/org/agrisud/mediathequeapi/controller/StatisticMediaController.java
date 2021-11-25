package org.agrisud.mediathequeapi.controller;

import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.model.GroupedStatistic;
import org.agrisud.mediathequeapi.model.StatisticData;
import org.agrisud.mediathequeapi.model.StatisticMedia;
import org.agrisud.mediathequeapi.service.StatisticMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic_media")
public class StatisticMediaController {
	@Autowired
	StatisticMediaService statisticMediaService;
	
	@PostMapping
	public void addStatisticMedia(@RequestBody StatisticMedia statisticMedia ) {
		  statisticMediaService.addStatisticMedia(statisticMedia);
	}
	
	@GetMapping
	public List<GroupedStatistic> getStatistic(@RequestParam(name = "year") String year,
												@RequestParam(name = "action") String action,
												@RequestParam(name = "typeCategory") String typeCategory,
												@RequestParam(name = "groupBy") String groupBy){
		return statisticMediaService.getStatistic(year,action,typeCategory,groupBy);
	}
	
	@GetMapping("/percentage")
	public Optional<StatisticData> getPercentage() {
		return statisticMediaService.getPercentage();
	}
	
}
