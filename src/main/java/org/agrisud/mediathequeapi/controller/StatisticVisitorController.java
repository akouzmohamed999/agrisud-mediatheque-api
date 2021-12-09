package org.agrisud.mediathequeapi.controller;

import java.util.List;

import org.agrisud.mediathequeapi.model.GroupedStatistic;
import org.agrisud.mediathequeapi.model.StatisticVisitor;
import org.agrisud.mediathequeapi.service.StatisticVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic_visitor")
public class StatisticVisitorController {
	@Autowired
	StatisticVisitorService statisticVisitorService;
	
	@PostMapping
	public void addStatisticVisitor(@RequestBody StatisticVisitor statisticVisitor ) {
		statisticVisitorService.addStatisticVisitor(statisticVisitor);
	}
	
	@GetMapping
	public List<GroupedStatistic> getStatistic(@RequestParam(name = "year") String year,
												@RequestParam(name = "groupBy") String groupBy){
		return statisticVisitorService.getStatistic(year,groupBy);
	}
}
