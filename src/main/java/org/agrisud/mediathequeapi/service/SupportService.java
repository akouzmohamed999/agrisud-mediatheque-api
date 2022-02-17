package org.agrisud.mediathequeapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.dao.CountryDao;
import org.agrisud.mediathequeapi.dao.DocumentTypeDao;
import org.agrisud.mediathequeapi.dao.ListCountrySupportDao;
import org.agrisud.mediathequeapi.dao.ListThematicSupportDao;
import org.agrisud.mediathequeapi.dao.StatisticMediaDao;
import org.agrisud.mediathequeapi.dao.SupportDao;
import org.agrisud.mediathequeapi.dao.ThematicDao;
import org.agrisud.mediathequeapi.enums.SortColumn;
import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.model.ListCountrySupport;
import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.agrisud.mediathequeapi.model.StatiscticCountView;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.model.Thematic;
import org.agrisud.mediathequeapi.search.SupportSearchQueries;
import org.agrisud.mediathequeapi.search.SupportSearchRepository;
import org.agrisud.mediathequeapi.search.SupportSearchQueries;
import org.agrisud.mediathequeapi.search.SupportSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SupportService {
    @Autowired
    ListCountrySupportDao listCountrySupportDao;
    @Autowired
    ListThematicSupportDao listThematicSupportDao;
    @Autowired
    SupportDao supportDao;
    @Autowired
    EventCloudDao eventCloudDao;
    @Autowired
    ThematicDao thematicDao;
    @Autowired
    DocumentTypeDao documentTypeDao;
    @Autowired
    CountryDao countryDao;
    @Autowired
    SupportSearchRepository supportSearchRepository;
    @Autowired
    SupportSearchQueries supportSearchQueries;
    @Autowired
    StatisticMediaDao statisticMediaDao;


    public Long addSupport(Support support) {
        ListCountrySupport listCountrySupport = new ListCountrySupport();
        ListThematicSupport listThematicSupport = new ListThematicSupport();
        if (support.getPathImage() != null) {
            support.setUrlImage(eventCloudDao.doShared(support.getPathImage()) + "/preview");
        }
        support.setUrlSupport(eventCloudDao.doShared(support.getPathSupport()));
        Long supportId = supportDao.addSupport(support);

        listCountrySupport.setSupportId(supportId);
        listThematicSupport.setSupportId(supportId);


        for (Country country : support.getListCountry()) {
            listCountrySupport.setCountryId(country.getCountryId());
            listCountrySupportDao.addListCountrySupport(listCountrySupport);
        }
        for (Thematic thematic : support.getListThematic()) {
            listThematicSupport.setThematicId(thematic.getThematicId());
            listThematicSupportDao.addListThematicSupport(listThematicSupport);
        }
        support.setSupportId(supportId);
        supportSearchRepository.save(support);
        return supportId;
    }


    public SearchPage<Support> getSupportBySearchCriteria(Map<String, Object> searchParams) {
        Optional<String> sortDirection = Optional.ofNullable((String) searchParams.get("sortDirection"));
        Optional<String> sortColumn = Optional.ofNullable((String) searchParams.get("sortColumn"));
        Optional<Sort> sort = Optional.empty();
        if (sortDirection.isPresent() && sortColumn.isPresent()) {
            sort = Optional.of(Sort.by("ASC".equals(searchParams.get("sortDirection")) ? Sort.Direction.ASC : Sort.Direction.DESC, (String) searchParams.get("sortColumn")));
        }
        PageRequest pageRequest = sort.map(orders -> PageRequest.of(Integer.parseInt((String) searchParams.get("page")), Integer.parseInt((String) searchParams.get("size")), orders))
                .orElseGet(() -> PageRequest.of(Integer.parseInt((String) searchParams.get("page")), Integer.parseInt((String) searchParams.get("size"))));
        String title = Optional.ofNullable((String) searchParams.get("title")).orElse(null);
        String countryId = Optional.ofNullable((String) searchParams.get("countryId")).orElse(null);
        String documentTypeId = Optional.ofNullable((String) searchParams.get("documentTypeId")).orElse(null);
        String thematicId = Optional.ofNullable((String) searchParams.get("thematicId")).orElse(null);
        String language = Optional.ofNullable((String) searchParams.get("language")).orElse(null);
        String dateSupport = Optional.ofNullable((String) searchParams.get("dateSupport")).orElse(null);
        Long categoryId = Optional.ofNullable((String) searchParams.get("categoryId")).map(Long::parseLong).orElse(null);
        SearchPage<Support> listSupport = supportSearchQueries.advancedSearch(title, countryId, documentTypeId, thematicId, language, dateSupport, categoryId, pageRequest);
        listSupport.forEach(support -> {
            List<Country> listCountry = new ArrayList<Country>();
            List<Thematic> listThematic = new ArrayList<Thematic>();
            support.getContent().getListCountry().forEach(country -> {
                listCountry.add(countryDao.getCountryById(country.getCountryId()));
            });
            support.getContent().setListCountry(listCountry);
            support.getContent().setDocumentType(documentTypeDao.getDocumentTypeById(support.getContent().getDocumentTypeId()));
            support.getContent().getListThematic().forEach(thematic -> {
                listThematic.add(thematicDao.getThematicById(thematic.getThematicId()));
            });
            support.getContent().setListThematic(listThematic);
            Optional<StatiscticCountView> statisticCountView = statisticMediaDao.getCountStatisticBymediaId(support.getContent().getCategoryId(), support.getContent().getSupportId());
            if (statisticCountView.isPresent() && !statisticCountView.isEmpty()) {
                support.getContent().setNumberDownload(statisticCountView.get().getNumberDownload());
                support.getContent().setNumberView(statisticCountView.get().getNumberView());
            } else {
                support.getContent().setNumberDownload(0);
                support.getContent().setNumberView(0);
            }
        });
        for (SearchHit<Support> support : listSupport.getContent()) {
            support.getContent().setDocumentType(documentTypeDao.getDocumentTypeById(support.getContent().getDocumentTypeId()));
        }
        return listSupport;
    }


    public Page<Support> getListSupport(Long categoryId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Thematic> list = new ArrayList<Thematic>();
        List<Country> listPays = new ArrayList<Country>();
        Page<Support> listSupport = supportDao.getListSupport(categoryId, pageRequest);
        List<ListCountrySupport> listPaysd = new ArrayList<ListCountrySupport>();
        for (Support support : listSupport) {
            list = new ArrayList<>();
            listPays = new ArrayList<>();
            for (ListCountrySupport listCountry : listCountrySupportDao.getListCountryBySupportId(support.getSupportId())) {
                listPays.add(countryDao.getCountryById(listCountry.getCountryId()));
            }
            for (ListThematicSupport listThematic : listThematicSupportDao.getListThematicBySupportId(support.getSupportId())) {
                list.add(thematicDao.getThematicById(listThematic.getThematicId()));
            }
            support.setListThematic(list);
            support.setListCountry(listPays);
            support.setDocumentType(documentTypeDao.getDocumentTypeById(support.getDocumentTypeId()));
        }

        return listSupport;
    }


    public void deleteSupport(Long id) {
        Support support = supportDao.getSupportById(id).get();
        if (support.getPathImage() != null && !"".equals(support.getPathImage())) {
            eventCloudDao.deleteFile(support.getPathImage());
        }
        if (support.getPathSupport() != null && support.getPathSupport() != "") {
            eventCloudDao.deleteFile(support.getPathSupport());
        }
        listThematicSupportDao.deleteListThematicBySupportId(support.getSupportId());
        listCountrySupportDao.deleteListCounrtyBySupportId(support.getSupportId());
        supportDao.deleteSupport(id);
        supportSearchRepository.delete(supportSearchRepository.findOneBySupportId(id));
    }

    public void updateSupport(Support support) {
        Support supportOld = supportDao.getSupportById(support.getSupportId()).get();
        support.setSupportESId(supportSearchRepository.findOneBySupportId(support.getSupportId()).getSupportESId());
        if (!supportOld.getPathSupport().equals(support.getPathSupport())) {
            eventCloudDao.deleteFile(supportOld.getPathSupport());
            support.setUrlSupport(eventCloudDao.doShared(support.getPathSupport()));
        }
        if (supportOld.getPathImage() != null) {
            if (!supportOld.getPathImage().equals(support.getPathImage())) {
                eventCloudDao.deleteFile(supportOld.getPathImage());

                if (!"".equals(support.getPathImage())) {
                    support.setUrlImage(eventCloudDao.doShared(support.getPathImage()) + "/preview");
                }
            }
        } else {
            if (support.getPathImage() != null && !"".equals(support.getPathImage())) {
                support.setUrlImage(eventCloudDao.doShared(support.getPathImage()) + "/preview");
            }
        }
        listThematicSupportDao.deleteListThematicBySupportId(support.getSupportId());
        listCountrySupportDao.deleteListCounrtyBySupportId(support.getSupportId());
        for (Thematic thematic : support.getListThematic()) {
            listThematicSupportDao.addListThematicSupport(
                    ListThematicSupport.builder()
                            .supportId(support.getSupportId())
                            .thematicId(thematic.getThematicId())
                            .build());
        }
        for (Country country : support.getListCountry()) {
            listCountrySupportDao.addListCountrySupport(
                    ListCountrySupport.builder().supportId(support.getSupportId()).countryId(country.getCountryId()).build()
            );
        }
//		supportSearchRepository.delete(supportSearchRepository.findOneBySupportId(support.getSupportId()));
        supportSearchRepository.save(support);
        supportDao.updateSupport(support);
    }


    public List<Support> getSupportByOrder(Long categoryId, SortColumn sortColumn, Boolean asc, String language) {
        List<Thematic> list = new ArrayList<Thematic>();
        List<Country> listPays = new ArrayList<Country>();
        List<Support> listSupport;
        if (asc) {
            listSupport = supportDao.getSupportByOrderASC(categoryId, sortColumn, language);
        } else {
            listSupport = supportDao.getSupportByOrderDESC(categoryId, sortColumn, language);
        }

        for (Support support : listSupport) {
            list = new ArrayList<>();
            listPays = new ArrayList<>();
            for (ListCountrySupport listCountry : listCountrySupportDao.getListCountryBySupportId(support.getSupportId())) {
                listPays.add(countryDao.getCountryById(listCountry.getCountryId()));
            }
            for (ListThematicSupport listThematic : listThematicSupportDao.getListThematicBySupportId(support.getSupportId())) {
                list.add(thematicDao.getThematicById(listThematic.getThematicId()));
            }
            support.setListCountry(listPays);
            support.setListThematic(list);
            support.setDocumentType(documentTypeDao.getDocumentTypeById(support.getDocumentTypeId()));
        }

        return listSupport;
    }


    public Support getSupportById(Long supportId) {
        Optional<Support> support = supportDao.getSupportById(supportId);
        if (support.isPresent()) {
            List<Thematic> list = new ArrayList<Thematic>();
            List<Country> listPays = new ArrayList<Country>();
            for (ListCountrySupport listCountry : listCountrySupportDao.getListCountryBySupportId(support.get().getSupportId())) {
                listPays.add(countryDao.getCountryById(listCountry.getCountryId()));
            }
            for (ListThematicSupport listThematic : listThematicSupportDao.getListThematicBySupportId(support.get().getSupportId())) {
                list.add(thematicDao.getThematicById(listThematic.getThematicId()));
            }
            support.get().setListCountry(listPays);
            support.get().setListThematic(list);
            support.get().setDocumentType(documentTypeDao.getDocumentTypeById(support.get().getDocumentTypeId()));
            return support.get();
        } else {
            return null;
        }

    }

    public List<Support> getAllSupport(Long categoryId) {
        return supportDao.getListAllSupport(categoryId);
    }


    public Support getLastNews() {
        return supportDao.getLastNews();

    }


}
