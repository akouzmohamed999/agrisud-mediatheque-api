package org.agrisud.mediathequeapi.controller;

import java.io.IOException;
import java.util.List;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.enums.SortColumn;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.model.SupportVideo;
import org.agrisud.mediathequeapi.service.SupportService;
import org.agrisud.mediathequeapi.service.SupportVideoService;
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
@RequestMapping("/support_video")
public class SupportVideoController {
	@Autowired
	SupportVideoService supportVideoService;
	@Autowired
	Utils util;
	@Autowired
	EventCloudService eventCloudService;


	@PostMapping
	public Long addSupportVideo(@RequestBody SupportVideo support) {
		return supportVideoService.addSupportVideo(support);
	}

//	@PostMapping(path = "/files" ,consumes = { MediaType.APPLICATION_JSON_VALUE, "multipart/form-data" })
//	@ResponseStatus(HttpStatus.OK)
//	public String uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam("path") String path) throws IOException {
//		return eventCloudService.uploadFile(multipartFile,path,util.generateKey(32));
//    }

	@GetMapping(path = "/{categoryId}")
	public List<SupportVideo> getListSupport(@PathVariable(name = "categoryId") Long categoryId) {
		return supportVideoService.getListSupportVideo(categoryId);
	}

	@DeleteMapping(path = "/{id}")
	public void deleteSupportVideo(@PathVariable(name = "id") Long id) {
		supportVideoService.deleteSupportVideo(id);
	}

	@PutMapping
	public void updateSupport(@RequestBody SupportVideo support) {
		supportVideoService.updateSupportVideo(support);
	}

	@GetMapping("/byOrder")
	public List<SupportVideo> getSupportVideoByOrder(
			@RequestParam(name = "categoryId") Long categoryId,
			@RequestParam(name = "sortColumn") SortColumn sortColumn,
			@RequestParam(name = "asc") Boolean asc,
			@RequestParam(name="language") String language){
		return supportVideoService.getSupportVideoByOrder(categoryId,sortColumn, asc,language);
	}

}
