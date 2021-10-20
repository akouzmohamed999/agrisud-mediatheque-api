package org.agrisud.mediathequeapi.controller;

import java.util.List;

import org.agrisud.mediathequeapi.model.NewsDto;
import org.agrisud.mediathequeapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {
	@Autowired
	NewsService newsService;
	
	@GetMapping
	public List<NewsDto> getListNews(){
		return newsService.getListNews();
	}
}
