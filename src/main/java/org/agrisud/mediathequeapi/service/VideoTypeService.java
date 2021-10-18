package org.agrisud.mediathequeapi.service;

import java.util.List;

import org.agrisud.mediathequeapi.clouddao.EventCloudDao;
import org.agrisud.mediathequeapi.dao.VideoTypeDao;
import org.agrisud.mediathequeapi.model.Thematic;
import org.agrisud.mediathequeapi.model.VideoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoTypeService {
	@Autowired
	VideoTypeDao videoTypeDao;
	@Autowired
	EventCloudDao eventCloudDao;
	
	public int addVideoType(VideoType videoType) {
		videoType.setUrlImage(eventCloudDao.doShared(videoType.getPathImage()));
		return videoTypeDao.addVideoType(videoType);
	}

	public int deleteVideoType(Long id) {
		VideoType videoType = videoTypeDao.getVideoTypeById(id);
		eventCloudDao.deleteFile(videoType.getPathImage());
		return videoTypeDao.deleteVideoType(id);
	}

	public int updateVideoType(VideoType videoType) {
		VideoType videoTypeOld = videoTypeDao.getVideoTypeById(videoType.getVideoTypeId());
		if(!videoTypeOld.getPathImage().equals(videoType.getPathImage())) {
			eventCloudDao.deleteFile(videoTypeOld.getPathImage());
			videoType.setUrlImage(eventCloudDao.doShared(videoType.getPathImage()));
		}
		return videoTypeDao.updateVideoType(videoType);
	}

	public List<VideoType> getAllVideoType() {
		return videoTypeDao.getAllVideoType();
	}

}
