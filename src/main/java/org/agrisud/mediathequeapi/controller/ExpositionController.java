package org.agrisud.mediathequeapi.controller;

import org.agrisud.mediathequeapi.cloudservice.ExpositionCloudService;
import org.agrisud.mediathequeapi.model.Exposition;
import org.agrisud.mediathequeapi.model.News;
import org.agrisud.mediathequeapi.service.ExpositionService;
import org.agrisud.mediathequeapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/exposition")
public class ExpositionController {
    @Autowired
    ExpositionService expositionService;
    @Autowired
    ExpositionCloudService expositionCloudService;
    @Autowired
	NewsService newsService;

    @PostMapping
    public Exposition addExposition(@RequestBody Exposition exposition) {
    	Exposition expositionNew = expositionService.getExpositionById(expositionService.addExposition(exposition));
    	newsService.addNews(News.builder().supportId(expositionNew.getExpositionId()).typeCategory("2").build());
        return expositionNew;
    }

    @PutMapping
    public Exposition updateExposition(@RequestBody Exposition exposition) {
         expositionService.getExpositionById(expositionService.updateExposition(exposition));
         newsService.addNews(News.builder().supportId(exposition.getExpositionId()).typeCategory("2").build());
         return exposition;
    }

    @GetMapping
    public List<Exposition> getListExposition(@RequestParam("categoryId") Long categoryId) throws IOException {
        return expositionService.getAllExpositionByCategory(categoryId);
    }

    @DeleteMapping(path ="/{id}")
    public int deleteExposition(@PathVariable("id") Long id) {
        int deleted =  expositionService.deleteExposition(id);
        newsService.deleteNewsBySupportId(id,"2");
        return deleted;
    }

}
