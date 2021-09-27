package org.agrisud.mediathequeapi.controller;

import java.util.List;

import org.agrisud.mediathequeapi.model.ListCountrySupport;
import org.agrisud.mediathequeapi.service.ListCountrySupportVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list_country_support_video")
public class ListCountrySupportVideoController {
	@Autowired
	ListCountrySupportVideoService listCountrySupportVideoService;
	@PostMapping
	public void addListCountrySupportVideo(@RequestBody ListCountrySupport listCountrySupport) {
		listCountrySupportVideoService.addListCountrySupportVideo(listCountrySupport);
	}
	
	@GetMapping
	public List<String> getListCountryBySupportVideoId(@RequestParam(name ="supportId") Long supportId) {
		return listCountrySupportVideoService.getListCountryBySupportVideoId(supportId);
	}
}
