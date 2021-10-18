package org.agrisud.mediathequeapi.controller;

import java.io.IOException;
import java.util.List;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.service.CountryService;
import org.agrisud.mediathequeapi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/country")
public class CountryController {
	@Autowired
	CountryService countryService;
	@Autowired
	EventCloudService eventCloudService;
	@Autowired
	Utils util;
	
	@PostMapping
	public int addCountry(@RequestBody Country country) {
		return countryService.addCountry(country);
	}
	
	@DeleteMapping(path ="/{id}")
	public int deleteCountry(@PathVariable("id") Long id) {
		Country country  = countryService.getCountryById(id);
		eventCloudService.deleteFile(country.getPathImage());
		return countryService.deleteCountry(id);
	}
	
	@PutMapping
	public int updateCountry(@RequestBody Country country) {
		return countryService.updateCountry(country);
	}
	@GetMapping
	public List<Country> getAllCountry() {
		return countryService.getAllCountry();
	}
	
	@GetMapping("/{id}")
	public Country getCountryById(@PathVariable("id") Long id) {
		return countryService.getCountryById(id);
	}
	
	@PostMapping(path = "/files" ,consumes = { MediaType.APPLICATION_JSON_VALUE, "multipart/form-data" })
	@ResponseStatus(HttpStatus.OK)
	public String uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam("path") String path) throws IOException {
		String generatedKey = util.generateKey(32);
		return eventCloudService.uploadFile(multipartFile,path,generatedKey);
    }
}
