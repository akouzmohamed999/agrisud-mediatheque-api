package org.agrisud.mediathequeapi.controller;

import org.agrisud.mediathequeapi.enums.SortColumn;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.Exposition;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.model.SupportVideo;
import org.agrisud.mediathequeapi.search.ExpositionSearchRepository;
import org.agrisud.mediathequeapi.search.SupportSearchRepository;
import org.agrisud.mediathequeapi.search.SupportVideoSearchRepository;
import org.agrisud.mediathequeapi.service.CategoryService;
import org.agrisud.mediathequeapi.service.ExpositionService;
import org.agrisud.mediathequeapi.service.SupportService;
import org.agrisud.mediathequeapi.service.SupportVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/synchro")
public class SynchroController {

    @Autowired
    ExpositionSearchRepository expositionSearchRepository;

    @Autowired
    SupportSearchRepository supportSearchRepository;

    @Autowired
    SupportVideoSearchRepository supportVideoSearchRepository;

    @Autowired
    ExpositionService expositionService;

    @Autowired
    SupportService supportService;

    @Autowired
    SupportVideoService supportVideoService;

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public void forceEsSynchro() throws IOException {
        expositionSearchRepository.deleteAll();
        supportSearchRepository.deleteAll();
        supportVideoSearchRepository.deleteAll();

        List<Category> categories = categoryService.getAllCategory();

        categories.forEach(category -> {
            List<Exposition> expositions = expositionService.getAllExpositionByCategory(category.getCategoryId());
            expositionSearchRepository.saveAll(expositions);
            List<Support> supports = supportService.getAllSupport(category.getCategoryId());
            supports.forEach(support -> support.setCategoryId(category.getCategoryId()));
            supportSearchRepository.saveAll(supports);
            List<SupportVideo> supportVideos = supportVideoService.getListSupportVideo(category.getCategoryId());
            supportVideos.forEach(supportVideo -> supportVideo.setCategoryId(category.getCategoryId()));
            supportVideoSearchRepository.saveAll(supportVideos);
        });
    }
}
