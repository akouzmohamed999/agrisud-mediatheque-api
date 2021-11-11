package org.agrisud.mediathequeapi.controller;

import java.util.List;

import org.agrisud.mediathequeapi.model.ThematicFolder;
import org.agrisud.mediathequeapi.model.ThematicFolderMedia;
import org.agrisud.mediathequeapi.service.ThematicFolderMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thematic_folder_media")
public class ThematicFolderMediaController {
	@Autowired
	ThematicFolderMediaService thematicFolderMediaService;
	
	
	@PostMapping
	public void addThematicFolderMedia(@RequestBody List<ThematicFolderMedia> listThematicFolderMedia){
		thematicFolderMediaService.addThematicFolderMedia(listThematicFolderMedia);
    }
	@GetMapping
	public List<ThematicFolderMedia> getThematicFolderMediaByParentId(@RequestParam(name="parentId")Long parentId) {
		return thematicFolderMediaService.getThematicFolderMediaByParentId(parentId);
	}
}
