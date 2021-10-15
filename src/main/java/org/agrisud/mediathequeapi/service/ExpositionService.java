package org.agrisud.mediathequeapi.service;

import org.agrisud.mediathequeapi.dao.*;
import org.agrisud.mediathequeapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpositionService {
    @Autowired
    ExpositionDao expositionDao;
    @Autowired
    ExpositionImageDao expositionImageDao;
    @Autowired
    ListExpositionImageDao listExpositionImageDao;
    @Autowired
    ListThematicExpositionDao listThematicExpositionDao;
    @Autowired
    ThematicDao thematicDao;

    public Long addExposition(Exposition exposition) {
        ListExpositionImage listExpositionImage = new ListExpositionImage();
        ListThematicExposition listThematicExposition = new ListThematicExposition();
        Long expositionId = expositionDao.addExposition(exposition);

        listExpositionImage.setExpositionId(expositionId);
        listThematicExposition.setExpositionId(expositionId);
        for (ExpositionImage expositionImage : exposition.getListExpositionImage()) {
            listExpositionImage.setExpositionImageId(expositionImage.getExpositionImageId());
            System.out.println(listExpositionImage);
            listExpositionImageDao.addListExpositionImage(listExpositionImage);
        }
        for (Thematic thematic : exposition.getListThematic()) {
            listThematicExposition.setThematicId(thematic.getThematicId());
            listThematicExpositionDao.addListThematicExposition(listThematicExposition);
        }
        return expositionId;
    }

    public Long updateExposition(Exposition exposition) {
        ListExpositionImage listExpositionImage = new ListExpositionImage();
        ListThematicExposition listThematicExposition = new ListThematicExposition();
        Long expositionId = exposition.getExpositionId();

        if(expositionDao.updateExposition(exposition) == 1){
            listExpositionImageDao.deleteListExpositionImageByExpositionId(exposition.getExpositionId());
            listThematicExpositionDao.deleteListThematicByExpositionId(exposition.getExpositionId());

            listExpositionImage.setExpositionId(expositionId);
            listThematicExposition.setExpositionId(expositionId);
            for (ExpositionImage expositionImage : exposition.getListExpositionImage()) {
                listExpositionImage.setExpositionImageId(expositionImage.getExpositionImageId());
                System.out.println(listExpositionImage);
                listExpositionImageDao.addListExpositionImage(listExpositionImage);
            }
            for (Thematic thematic : exposition.getListThematic()) {
                listThematicExposition.setThematicId(thematic.getThematicId());
                listThematicExpositionDao.addListThematicExposition(listThematicExposition);
            }
        }
        return expositionId;
    }

    public int deleteExposition(Long id) {
        expositionImageDao.deleteExpositionImageByIdExposition(id);
        listExpositionImageDao.deleteListExpositionImageByExpositionId(id);
        listThematicExpositionDao.deleteListThematicByExpositionId(id);
        return expositionDao.deleteExposition(id);
    }

    public List<Exposition> getAllExpositionByCategory(Long idCategory) {
        List<Exposition> expositionList = expositionDao.getListExpositionByCategory(idCategory);
        expositionList.forEach(exposition -> {
            exposition.setListExpositionImage(expositionImageDao.getListExpositionImageByExpositionId(exposition.getExpositionId()));
            exposition.setListThematic(thematicDao.getListThematicByExpositionId(exposition.getExpositionId()));
        });
        return expositionList;
    }

    public Exposition getExpositionById(Long id) {
        return expositionDao.getExpositionById(id);
    }
}
