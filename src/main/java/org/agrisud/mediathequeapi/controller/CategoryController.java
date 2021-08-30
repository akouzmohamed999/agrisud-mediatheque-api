package org.agrisud.mediathequeapi.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import javax.websocket.server.PathParam;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.service.CategoryService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;

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
	
	@GetMapping(path = "/files",produces = MediaType.IMAGE_JPEG_VALUE)
	public String  /*ResponseEntity<Resource>*/ getFile(@PathParam("fileName") String fileName ) throws IOException {
		 InputStream inputStream ; 
//		 byte[] media;
//		 
		 inputStream = categoryService.getFile(fileName);
//		 media = inputStream.readAllBytes();
//		 //media =  IOUtils.toByteArray(inputStream);
//			 
//		 IOUtils.close();
//		 IOUtils.close(inputStream);
//		 inputStream.close();
//		 
//		 String imageStr = Base64.encodeBase64String(media);
//		 InputStreamResource resource = new InputStreamResource(inputStream);
//		 return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageStr);
		 StringBuilder resultStringBuilder = new StringBuilder();
		    try (BufferedReader br
		      = new BufferedReader(new InputStreamReader(inputStream))) {
		        String line;
		        while ((line = br.readLine()) != null) {
		            resultStringBuilder.append(line).append("\n");
		        }
		    }
		    return resultStringBuilder.toString();
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
