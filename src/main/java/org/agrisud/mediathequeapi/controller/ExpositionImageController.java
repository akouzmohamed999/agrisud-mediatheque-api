package org.agrisud.mediathequeapi.controller;

import org.agrisud.mediathequeapi.cloudservice.ExpositionCloudService;
import org.agrisud.mediathequeapi.model.ExpositionImage;
import org.agrisud.mediathequeapi.service.ExpositionImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/exposition-image")
public class ExpositionImageController {
    @Autowired
    ExpositionImageService expositionImageService;
    @Autowired
    ExpositionCloudService expositionCloudService;

    @PostMapping(path = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, "multipart/form-data"})
    @ResponseStatus(HttpStatus.OK)
    public ExpositionImage addExpositionImage(@RequestParam("file") MultipartFile multipartFile, @RequestParam("exposition_name") String expositionName, @RequestParam("rank") Long rank) throws IOException {
        try {
            List<String> list = expositionCloudService.uploadFile(multipartFile, expositionName);
            ExpositionImage expositionImage = new ExpositionImage();
            expositionImage.setUrlImage(list.get(0));
            expositionImage.setPathImage(list.get(1));
            expositionImage.setName("");
            expositionImage.setRankImg(rank);
            return expositionImageService.getExpositionImageById(expositionImageService.addExpositionImage(expositionImage));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @GetMapping
    public List<ExpositionImage> getListExpositionImageByExpositionId(@RequestParam("expositionId") Long expositionId) throws IOException {
        return expositionImageService.getListExpositionImageByExpositionId(expositionId);
    }

    @DeleteMapping(path = "/{id}")
    public int deleteExpositionImage(@PathVariable("id") Long id) {
        return expositionImageService.deleteExpositionImage(id);
    }

    @DeleteMapping(path = "/list-exposition-image/{id}")
    public int deleteExpositionImageById(@PathVariable("id") Long id) {
        return expositionImageService.deleteListExpositionImageByExpositionImageId(id);
    }

    @PutMapping(path = "/{idExposition}/{rank}")
    public ExpositionImage updateExpositionImage(@PathVariable("idExposition") Long id, @PathVariable("rank") int rank) {
        return expositionImageService.updateExpositionImage(id, rank);
    }
}
