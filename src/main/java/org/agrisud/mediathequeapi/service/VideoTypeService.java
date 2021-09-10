package org.agrisud.mediathequeapi.service;

import java.util.List;

import org.agrisud.mediathequeapi.dao.VideoTypeDao;
import org.agrisud.mediathequeapi.model.VideoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoTypeService {
	@Autowired
	VideoTypeDao videoTypeDao;
	
	public int addVideoType(VideoType videoType) {
		return videoTypeDao.addVideoType(videoType);
	}

	public int deleteVideoType(Long id) {
		return videoTypeDao.deleteVideoType(id);
	}

	public int updateVideoType(VideoType videoType) {
		return videoTypeDao.updateVideoType(videoType);
	}

	public List<VideoType> getAllVideoType() {
		return videoTypeDao.getAllVideoType();
	}

}
