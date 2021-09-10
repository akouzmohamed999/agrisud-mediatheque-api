package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.DocumentType;
import org.agrisud.mediathequeapi.model.VideoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/video_type.properties")
public class VideoTypeDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;
	
	public int addVideoType(VideoType videoType) {
		return jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_VIDEO_TYPE), getSqlParameterSource(videoType));
	}
	
	private MapSqlParameterSource getSqlParameterSource(VideoType videoType) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.VIDEO_TYPE_ID, videoType.getVideoTypeId())
				.addValue(DaoConstant.TITLE, videoType.getTitle());
	}

	public int deleteVideoType(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.VIDEO_TYPE_ID, id);
		return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_VIDEO_TYPE_BY_ID), new MapSqlParameterSource(params));
	}

	public int updateVideoType(VideoType videoType) {
		return jdbcTemplate.update(environment.getProperty(SqlConstant.UPDATE_VIDEO_TYPE),
				getSqlParameterSource(videoType));
	}

	public List<VideoType> getAllVideoType() {
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_ALL_VIDEO_TYPE), VideoType::baseMapper);
	}
}
