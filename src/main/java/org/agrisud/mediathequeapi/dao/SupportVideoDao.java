package org.agrisud.mediathequeapi.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.enums.SortColumn;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.model.SupportVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
@Repository
@PropertySource("classpath:sql/support_video.properties")
public class SupportVideoDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;
	
	public Long addSupport(SupportVideo support) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_SUPPORT_VIDEO), getSqlParameterSource(support),holder);
		return holder.getKey().longValue();
	}
	
	private MapSqlParameterSource getSqlParameterSource(SupportVideo support) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.SUPPORT_ID, support.getSupportId())
				.addValue(DaoConstant.CATEGORY_ID, support.getCategoryId())
				.addValue(DaoConstant.TITLE, support.getTitle())
				.addValue(DaoConstant.PATH_SUPPORT, support.getPathSupport())
				.addValue(DaoConstant.URL_SUPPORT, support.getUrlSupport())
				.addValue(DaoConstant.LANGUAGE, support.getLanguage())
				.addValue(DaoConstant.DATE_SUPPORT, support.getDateSupport())
				.addValue(DaoConstant.LINK, support.getLink())
				.addValue(DaoConstant.VIDEO_TYPE_ID, support.getVideoTypeId());
	}

	public SupportVideo getSupportVideoById(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, id);
		try {
			return jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_SUPPORT_VIDEO_BY_ID),
					new MapSqlParameterSource(params), getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	private RowMapper<SupportVideo> getRowMapper() {
		return (rs, rowNum) -> SupportVideo.builder()
				.supportId(rs.getLong(DaoConstant.SUPPORT_ID))
				.title(rs.getString(DaoConstant.TITLE))
				.pathSupport(rs.getString(DaoConstant.PATH_SUPPORT))
				.urlSupport(rs.getString(DaoConstant.URL_SUPPORT))
				.dateSupport(rs.getString(DaoConstant.DATE_SUPPORT))
				.videoTypeId(rs.getLong(DaoConstant.VIDEO_TYPE_ID))
				.language(rs.getString(DaoConstant.LANGUAGE))
				.link(rs.getString(DaoConstant.LINK))
				.build();
	}

	public void deleteSupportVideo(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, id);
		jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_SUPPORT_VIDEO_BY_ID), new MapSqlParameterSource(params));
		
	}

	public List<SupportVideo> getListSupportVideo(Long categoryId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, categoryId);
		 return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_VIDEO),new MapSqlParameterSource(params), getRowMapper());
	}

	public void updateSupportVideo(SupportVideo support) {
		jdbcTemplate.update(environment.getProperty(SqlConstant.UPDATE_SUPPORT_VIDEO),
				getSqlParameterSource(support));
	}

	public List<SupportVideo> getSupportVideoByOrderASC(Long categoryId, SortColumn sortColumn) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, categoryId);
		
        switch (sortColumn) {
            case SUPPORT_TITLE -> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_VIDEO_ORDER_BY_TITLE_ASC),new MapSqlParameterSource(params), getRowMapper());
            }
            case SUPPORT_DATE -> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_VIDEO_ORDER_BY_DATE_ASC),new MapSqlParameterSource(params), getRowMapper());
            }
            case SUPPORT_VIDEO_TYPE-> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_VIDEO_ORDER_BY_VIDEO_TYPE_ASC),new MapSqlParameterSource(params), getRowMapper());
            }
            default -> {
            	return new ArrayList<SupportVideo>();
            }
        }
	}

	public List<SupportVideo> getSupportVideoByOrderDESC(Long categoryId, SortColumn sortColumn) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, categoryId);
		
        switch (sortColumn) {
            case SUPPORT_TITLE -> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_VIDEO_ORDER_BY_TITLE_DESC),new MapSqlParameterSource(params), getRowMapper());
            }
            case SUPPORT_DATE -> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_VIDEO_ORDER_BY_DATE_DESC),new MapSqlParameterSource(params), getRowMapper());
            }
            case SUPPORT_VIDEO_TYPE-> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_VIDEO_ORDER_BY_VIDEO_TYPE_DESC),new MapSqlParameterSource(params), getRowMapper());
            }
            default -> {
            	return new ArrayList<SupportVideo>();
            }
        }
	}

}
