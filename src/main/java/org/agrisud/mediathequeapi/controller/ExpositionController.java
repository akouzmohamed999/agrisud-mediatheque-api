package org.agrisud.mediathequeapi.controller;

import org.agrisud.mediathequeapi.cloudservice.ExpositionCloudService;
import org.agrisud.mediathequeapi.model.Exposition;
import org.agrisud.mediathequeapi.service.ExpositionService;
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

    @PostMapping
    public Exposition addExposition(@RequestBody Exposition exposition) {
        return expositionService.getExpositionById(expositionService.addExposition(exposition));
    }

    @PutMapping
    public Exposition updateExposition(@RequestBody Exposition exposition) {
        return expositionService.getExpositionById(expositionService.updateExposition(exposition));
    }

    @GetMapping
    public List<Exposition> getListExposition(@RequestParam("categoryId") Long categoryId) throws IOException {
        return expositionService.getAllExpositionByCategory(categoryId);
    }

    @DeleteMapping(path ="/{id}")
    public int deleteExposition(@PathVariable("id") Long id) {
        return expositionService.deleteExposition(id);
    }

}
