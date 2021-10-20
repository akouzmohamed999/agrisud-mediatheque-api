package org.agrisud.mediathequeapi.service;

import java.util.ArrayList;
import java.util.List;

import org.agrisud.mediathequeapi.dao.NewsDao;
import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.model.DocumentType;
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
	
	public void addNews(News news) {
		int total = newsDao.getCountNews();
		if(total >= 4) {
			newsDao.deleteFirstNews();
		}
			newsDao.addNews(news);
	}
	
	public void deleteNewsBySupportId(Long supportId) {
		newsDao.deleteNewsBySupportId(supportId);
	}
	
	public List<NewsDto> getListNews(){
		List<NewsDto> listNewsDto = new ArrayList<>();
		NewsDto newsDto = new NewsDto();
		for(News news : newsDao.getListNews()) {
			newsDto = new NewsDto();
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
			}else {
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
			}
			listNewsDto.add(newsDto);
		}
		return listNewsDto;
	}
}
