package org.agrisud.mediathequeapi.controller;

import java.io.IOException;
import java.util.List;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.model.VideoType;
import org.agrisud.mediathequeapi.service.VideoTypeService;
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
@RequestMapping("/video_type")
public class VideoTypeController {
	@Autowired
	VideoTypeService videoTypeService;
	@Autowired
	EventCloudService eventCloudService;
	@Autowired
	Utils util;

	@PostMapping
	public int addVideoType(@RequestBody VideoType videoType) {
		return videoTypeService.addVideoType(videoType);
	}
	
	@DeleteMapping(path ="/{id}")
	public int deleteVideoType(@PathVariable("id") Long id) {
		
		return videoTypeService.deleteVideoType(id);
	}
	
	@PutMapping
	public int updateVideoType(@RequestBody VideoType videoType) {
		return videoTypeService.updateVideoType(videoType);
	}
	
	@GetMapping
	public List<VideoType> getAllVideoType() {
		return videoTypeService.getAllVideoType();
	}
	
	@PostMapping(path = "/files" ,consumes = { MediaType.APPLICATION_JSON_VALUE, "multipart/form-data" })
	@ResponseStatus(HttpStatus.OK)
	public String uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam("path") String path) throws IOException {
		String generatedKey = util.generateKey(32);
		return eventCloudService.uploadFile(multipartFile,path,generatedKey);
    }
}
