package org.agrisud.mediathequeapi.controller;

import org.agrisud.mediathequeapi.model.ListCountrySupport;
import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.agrisud.mediathequeapi.service.ListCountrySupportService;
import org.agrisud.mediathequeapi.service.ListThematicSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/list_country_support")
public class ListCountrySupportController {
	@Autowired
	ListCountrySupportService listCountrySupportService;
	@PostMapping
	public void addListCountrySupport(@RequestBody ListCountrySupport listCountrySupport) {
		listCountrySupportService.addListCountrySupport(listCountrySupport);
	}
}