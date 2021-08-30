package org.agrisud.mediathequeapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	@Autowired
    EventCloudService eventCloudService;
	
	@PostMapping
	public void createFolder(@RequestBody Category category)
    {
		categoryService.addCategory(category);
		
    }
	@PostMapping(path = "/files" ,consumes = { MediaType.APPLICATION_JSON_VALUE, "multipart/form-data" })
	@ResponseStatus(HttpStatus.OK)
	public String uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam("path") String path) throws IOException {
		return categoryService.uploadFile(multipartFile,path);
    }
	
	
	@GetMapping
	public List<Category> getAllCategory() throws IOException {
		return categoryService.getAllCategory();
	}
	
	@DeleteMapping(path ="/{id}")
	public int deleteCategory(@PathVariable("id") Long id) {
		return categoryService.deleteCategory(id);
	}
	
	@GetMapping(path ="/{id}")
	public Optional<Category> getCategoryById(@PathVariable(name = "id") Long id) {
		return categoryService.getCategoryById(id);
	}
	
	@GetMapping(path = "/search")
	public List<Category> getCategoryByTitle(@RequestParam("title") String title){
		return categoryService.getCategoryByTitle(title);
	}
	@GetMapping(path="/title")
	public Boolean checkTitleIfExist(@RequestParam("pathFolder") String pathFolder,
							  @RequestParam("title") String title) {
		return categoryService.checkTitleIfExist(pathFolder,title);
	}
	@GetMapping(path="/child")
	public List<Category> getChildOfLevel(@RequestParam("pathFolder") String pathFolder) {
		return categoryService.getChildOfLevel(pathFolder);
	}
}
