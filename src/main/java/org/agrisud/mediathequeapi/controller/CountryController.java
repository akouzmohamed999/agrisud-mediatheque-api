package org.agrisud.mediathequeapi.controller;

import java.util.List;

import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.model.Thematic;
import org.agrisud.mediathequeapi.service.CategoryService;
import org.agrisud.mediathequeapi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
public class CountryController {
//	@Autowired
//	CountryService countryService;
//	
//	
//	@PostMapping
//	public int addCountry(@RequestBody Country country) {
//		return countryService.addCountry(country);
//	}
//	
//	@DeleteMapping(path ="/{id}")
//	public int deleteCountry(@PathVariable("id") Long id) {
//		return countryService.deleteCountry(id);
//	}
//	
//	@PutMapping
//	public int updateCountry(@RequestBody Country country) {
//		return countryService.updateCountry(country);
//	}
//	@GetMapping
//	public List<Thematic> getAllCountry() {
//		return countryService.getAllCountry();
//	}
}
