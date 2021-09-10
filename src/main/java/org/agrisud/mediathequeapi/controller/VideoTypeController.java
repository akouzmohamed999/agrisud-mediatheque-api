package org.agrisud.mediathequeapi.controller;

import java.util.List;

import org.agrisud.mediathequeapi.model.DocumentType;
import org.agrisud.mediathequeapi.model.VideoType;
import org.agrisud.mediathequeapi.service.DocumentTypeService;
import org.agrisud.mediathequeapi.service.VideoTypeService;
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
@RequestMapping("/video_type")
public class VideoTypeController {
	@Autowired
	VideoTypeService videoTypeService;
	

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
}
