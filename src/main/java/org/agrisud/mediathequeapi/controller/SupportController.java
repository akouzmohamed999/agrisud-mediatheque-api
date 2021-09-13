package org.agrisud.mediathequeapi.controller;

import java.io.IOException;
import java.util.List;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.service.SupportService;
import org.agrisud.mediathequeapi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/support")
public class SupportController {
	public static final String PAGE = "0";
    public static final String SIZE = "5";

	@Autowired
	SupportService supportService;
	@Autowired
	Utils util;
	@Autowired
    EventCloudService eventCloudService;
	
	
	@PostMapping
	public Long addSupport(@RequestBody Support support) {
		return supportService.addSupport(support);
	}
	
	@PostMapping(path = "/files" ,consumes = { MediaType.APPLICATION_JSON_VALUE, "multipart/form-data" })
	@ResponseStatus(HttpStatus.OK)
	public String uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam("path") String path) throws IOException {
		return eventCloudService.uploadFile(multipartFile,path,util.generateKey(32));
    }
	
	@GetMapping
	public List<Support> getListSupport() {
		return supportService.getListSupport();
	} 
	
	
}
