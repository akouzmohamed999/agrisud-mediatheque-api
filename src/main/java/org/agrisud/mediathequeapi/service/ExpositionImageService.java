package org.agrisud.mediathequeapi.service;

import org.agrisud.mediathequeapi.clouddao.ExpositionCloudDao;
import org.agrisud.mediathequeapi.dao.*;
import org.agrisud.mediathequeapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpositionImageService {
    @Autowired
    ExpositionImageDao expositionImageDao;
    @Autowired
    ListExpositionImageDao listExpositionImageDao;
    @Autowired
    ExpositionCloudDao expositionCloudDao;

    public Long addExpositionImage(ExpositionImage expositionImage) {
        return expositionImageDao.addExpositionImage(expositionImage);
    }

    public int deleteExpositionImage(Long expositionImageId) {
        return expositionImageDao.deleteExpositionImage(expositionImageId);
    }

    public List<ExpositionImage> getAllExpositionImage() {
        return expositionImageDao.getListExpositionImage();
    }

    public List<ExpositionImage> getListExpositionImageByExpositionId(Long expositionId) {
        return expositionImageDao.getListExpositionImageByExpositionId(expositionId);
    }

    public ExpositionImage getExpositionImageById(Long id) {
        return expositionImageDao.getExpositionImageById(id);
    }

    public int deleteExpositionImageById(Long expositionImageId) {
        return expositionImageDao.deleteExpositionImageById(expositionImageId);
    }

    public ExpositionImage updateExpositionImage(Long expositionImageId, int rank) {
        expositionImageDao.updateExpositionImage(expositionImageId, rank);
        return expositionImageDao.getExpositionImageById(expositionImageId);
    }

    public int deleteListExpositionImageByExpositionImageId(Long expositionImageId) {
        try {
            expositionImageDao.getExpositionImageById(expositionImageId).getPathImage();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        listExpositionImageDao.deleteListExpositionImageByExpositionImageId(expositionImageId);
        return expositionImageDao.deleteExpositionImageById(expositionImageId);
    }
}
