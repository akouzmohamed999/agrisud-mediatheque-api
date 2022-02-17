package org.agrisud.mediathequeapi.controller;

import org.agrisud.mediathequeapi.enums.SortColumn;
import org.agrisud.mediathequeapi.model.*;
import org.agrisud.mediathequeapi.search.ExpositionSearchRepository;
import org.agrisud.mediathequeapi.search.SupportSearchRepository;
import org.agrisud.mediathequeapi.search.SupportVideoSearchRepository;
import org.agrisud.mediathequeapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    ListCountrySupportService listCountrySupportService;

    @Autowired
    CountryService countryService;

    @Autowired
    ListThematicSupportService listThematicSupportService;

    @Autowired
    ThematicService thematicService;

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    ListCountrySupportVideoService listCountrySupportVideoService;

    @Autowired
    ListThematicSupportVideoService listThematicSupportVideoService;

    @Autowired
    VideoTypeService videoTypeService;

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
            for (Support support : supports) {
                List<Thematic> list = new ArrayList<>();
                List<Country>  listPays = new ArrayList<>();
                for (ListCountrySupport listCountry : listCountrySupportService.getListCountryBySupportId(support.getSupportId())) {
                    listPays.add(countryService.getCountryById(listCountry.getCountryId()));
                }
                for (ListThematicSupport listThematic : listThematicSupportService.getListThematicBySupportId(support.getSupportId())) {
                    list.add(thematicService.getThematicById(listThematic.getThematicId()));
                }
                support.setListCountry(listPays);
                support.setListThematic(list);
                support.setDocumentType(documentTypeService.getDocumentTypeById(support.getDocumentTypeId()));
                support.setCategoryId(category.getCategoryId());
            }
            supportSearchRepository.saveAll(supports);
            List<SupportVideo> supportVideos = supportVideoService.getListSupportVideo(category.getCategoryId());
            for(SupportVideo support : supportVideos) {
                List<Thematic> list = new ArrayList<>();
                List<Country>  listPays = new ArrayList<>();
                for(ListCountrySupport listCountry : listCountrySupportVideoService.getListCountryBySupportVideoId(support.getSupportId())) {
                    listPays.add(countryService.getCountryById(listCountry.getCountryId()));
                }
                for(ListThematicSupport listThematic : listThematicSupportVideoService.getListThematicBySupportVideoId(support.getSupportId())) {
                    list.add(thematicService.getThematicById(listThematic.getThematicId()));
                }
                support.setListCountry(listPays);
                support.setListThematic(list);
                support.setVideoType(videoTypeService.getVideoTypeById(support.getVideoTypeId()));
                support.setCategoryId(category.getCategoryId());
            }
            supportVideoSearchRepository.saveAll(supportVideos);
        });
    }
}
