package org.agrisud.mediathequeapi.controller;

import java.io.IOException;
import java.util.List;

import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.ThematicFolder;
import org.agrisud.mediathequeapi.service.ThematicFolderService;
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
@RequestMapping("/thematic_folder")
public class ThematicFolderController {
	@Autowired
	ThematicFolderService thematicFolderService; 
	
	@PostMapping
	public void addThematicFolder(@RequestBody ThematicFolder thematicFolder){
		thematicFolderService.addThematicFolder(thematicFolder);
    }
	
	@PostMapping(path = "/files" ,consumes = { MediaType.APPLICATION_JSON_VALUE, "multipart/form-data" })
	@ResponseStatus(HttpStatus.OK)
	public String uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam("path") String path) throws IOException {
		return thematicFolderService.uploadFile(multipartFile,path);
    }
	
	@GetMapping
	public List<ThematicFolder> getThematicFolderByParentId(@RequestParam(name = "parentId") Long parentId){
		return thematicFolderService.getThematicFolderByParentId(parentId);
	}
	
	@PutMapping(path = "/files" ,consumes = { MediaType.APPLICATION_JSON_VALUE, "multipart/form-data" })
	@ResponseStatus(HttpStatus.OK)
	public String updateFiles(@RequestParam("file") MultipartFile multipartFile,
							  @RequestParam("path") String path) throws IOException {
		return thematicFolderService.updateFiles(multipartFile,path);
	}
	
	@PutMapping
	public int updateThematicFolder(@RequestBody ThematicFolder thematicFolder) {
		return thematicFolderService.updateThematicFolder(thematicFolder);
	}
	
	@DeleteMapping(path ="/{id}")
	public int deleteThematicFolderById(@PathVariable("id") Long id) {
		return thematicFolderService.deleteThematicFolderById(id);
	}
	
	@GetMapping(path = "/parent")
	public List<ThematicFolder> getAllParent(@RequestParam(name="parentId")Long parentId){
		return thematicFolderService.getAllParent(parentId);
	}
}
