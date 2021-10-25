package org.agrisud.mediathequeapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.agrisud.mediathequeapi.dao.NewsDao;
import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.model.DocumentType;
import org.agrisud.mediathequeapi.model.Exposition;
import org.agrisud.mediathequeapi.model.News;
import org.agrisud.mediathequeapi.model.NewsDto;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.model.SupportVideo;
import org.agrisud.mediathequeapi.model.Thematic;
import org.agrisud.mediathequeapi.model.VideoType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
	@Autowired
	NewsDao newsDao;
	@Autowired
	SupportService supportService;
	@Autowired
	SupportVideoService supportVideoService;
	@Autowired
    ExpositionService expositionService;
	
	public void addNews(News news) {
		int total = newsDao.getCountNews();
		if(total >= 3) {
			newsDao.deleteFirstNews();
		}
			newsDao.addNews(news);
	}
	
	public void deleteNewsBySupportId(Long supportId,String typeCategory) {
		newsDao.deleteNewsBySupportId(supportId,typeCategory);
		checkNews();
	}
	
	public void checkNews() {
//		Support support = supportService.getLastNews();
//		SupportVideo supportVideo = supportVideoService.getLastNews();
//		Exposition exposition  = expositionService.getLastNews();
//		if(support.getUpdateAt().after(supportVideo.getUpdateAt()) && support.getUpdateAt().after(exposition.getUpdateAt())) {
//			addNews(News.builder().supportId(support.getSupportId()).typeCategory("0").build());
//		}else if(supportVideo.getUpdateAt().after(support.getUpdateAt()) && supportVideo.getUpdateAt().after(exposition.getUpdateAt())){
//			addNews(News.builder().supportId(supportVideo.getSupportId()).typeCategory("1").build());
//		}else {
//			addNews(News.builder().supportId(exposition.getExpositionId()).typeCategory("2").build());
//		}
		Optional<News> lastNews = newsDao.getLastNews();
		if(lastNews.isPresent()) {
			addNews(lastNews.get());
		}
	}
	
	public List<NewsDto> getListNews(){
		List<NewsDto> listNewsDto = new ArrayList<>();
		NewsDto newsDto = new NewsDto();
		for(News news : newsDao.getListNews()) {
			newsDto = new NewsDto();
			newsDto.setTypeCategory(news.getTypeCategory());
			if("0".equals(news.getTypeCategory() ) ) {
				Support support = supportService.getSupportById(news.getSupportId());
				newsDto.setSupportId(support.getSupportId());
			    newsDto.setTitle(support.getTitle());
			    newsDto.setPathSupport(support.getPathSupport());
			    newsDto.setPathImage(support.getPathImage());
			    newsDto.setUrlImage(support.getUrlImage());
			    newsDto.setUrlSupport(support.getUrlSupport());
			    newsDto.setLanguage(support.getLanguage());
			    newsDto.setListCountry(support.getListCountry());
			    newsDto.setListThematic(support.getListThematic());
			    newsDto.setDocumentType(support.getDocumentType());
			    newsDto.setDateSupport(support.getDateSupport());
			    newsDto.setDownload(support.isDownload());
			    
			}else if("1".equals(news.getTypeCategory() )) {
				SupportVideo support = supportVideoService.getSupportVideoById(news.getSupportId());
				newsDto.setSupportId(support.getSupportId());
			    newsDto.setTitle(support.getTitle());
			    newsDto.setPathImage(support.getPathSupport());
			    newsDto.setUrlImage(support.getUrlSupport());
			    newsDto.setUrlSupport(support.getLink());
			    newsDto.setLanguage(support.getLanguage());
			    newsDto.setListCountry(support.getListCountry());
			    newsDto.setListThematic(support.getListThematic());
			    newsDto.setVideoType(support.getVideoType());
			    newsDto.setDateSupport(support.getDateSupport());
			}else {
				Exposition exposition = expositionService.getExpositionById(news.getSupportId());
				newsDto.setSupportId(exposition.getExpositionId());
				newsDto.setTitle(exposition.getTitleEn());
				newsDto.setListCountry(exposition.getListCountry());
				newsDto.setListThematic(exposition.getListThematic());
				newsDto.setDateSupport(exposition.getDateExposition());
				newsDto.setListExpositionImage(exposition.getListExpositionImage());
				newsDto.setUrlImage(exposition.getListExpositionImage().get(0).getUrlImage());
				
			}
			listNewsDto.add(newsDto);
		}
		return listNewsDto;
	}
}
