package org.agrisud.mediathequeapi.controller;

import org.agrisud.mediathequeapi.cloudservice.EventCloudService;
import org.agrisud.mediathequeapi.enums.SortColumn;
import org.agrisud.mediathequeapi.model.News;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.service.NewsService;
import org.agrisud.mediathequeapi.service.SupportService;
import org.agrisud.mediathequeapi.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	@Autowired
	NewsService newsService;


	@PostMapping
	public Long addSupport(@RequestBody Support support) {
		Long supportId =  supportService.addSupport(support);
//		newsService.addNews(News.builder().supportId(supportId).typeCategory("0").build());
		return supportId;
	}
	
	@PostMapping(path = "/files" ,consumes = { MediaType.APPLICATION_JSON_VALUE, "multipart/form-data" })
	@ResponseStatus(HttpStatus.OK)
	public String uploadFile(@RequestParam("file") MultipartFile multipartFile,@RequestParam("path") String path) throws IOException {
		return eventCloudService.uploadFile(multipartFile,path,util.generateKey(32));
    }
	
	@GetMapping(path = "/{categoryId}")
	public Page<Support> getListSupport(@PathVariable(name = "categoryId") Long categoryId,
										@RequestParam(name = "page", defaultValue = PAGE) int page,
										@RequestParam(name = "size", defaultValue = SIZE) int size
) {
		return supportService.getListSupport(categoryId,page,size);
	}

	@DeleteMapping(path = "/{id}")
	public void deleteSupport(@PathVariable(name = "id") Long id) {
		supportService.deleteSupport(id);
//		newsService.deleteNewsBySupportId(id,"0");
	}

	@PutMapping
	public void updateSupport(@RequestBody Support support) {
//		newsService.addNews(News.builder().supportId(support.getSupportId()).typeCategory("0").build());
		supportService.updateSupport(support);
	}
	
	 @GetMapping("/byOrder")
	    public List<Support> getSupportByOrder(
	    									@RequestParam(name = "categoryId") Long categoryId,
                                            @RequestParam(name = "sortColumn") SortColumn sortColumn,
                                            @RequestParam(name= "language") String language,
                                            @RequestParam(name = "asc") Boolean asc){
	        return supportService.getSupportByOrder(categoryId,sortColumn, asc,language);
	    }

    @GetMapping("/search")
    public SearchPage<Support> getSupportsBySearchCriteria(@RequestParam Map<String, Object> params) {
        return supportService.getSupportBySearchCriteria(params);
    }

}
