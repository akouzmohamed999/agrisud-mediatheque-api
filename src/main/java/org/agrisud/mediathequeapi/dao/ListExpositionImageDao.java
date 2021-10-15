package org.agrisud.mediathequeapi.dao;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.ListExpositionImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@PropertySource("classpath:sql/list_exposition_exposition_image.properties")
public class ListExpositionImageDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;
	
	public void addListExpositionImage(ListExpositionImage listExpositionImage) {
		System.out.println("add1");
		jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_LIST_EXPOSITION_EXPOSITION_IMAGE), getSqlParameterSource(listExpositionImage));
	}
	
	private MapSqlParameterSource getSqlParameterSource(ListExpositionImage listExpositionImage) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.LIST_THEMATIC_EXPOSITION_ID, listExpositionImage.getListExpositionImageId())
				.addValue(DaoConstant.EXPOSITION_ID, listExpositionImage.getExpositionId())
				.addValue(DaoConstant.EXPOSITION_IMAGE_ID, listExpositionImage.getExpositionImageId());
	}

	public List<ListExpositionImage> getListExpositionImageByExpositionId(Long expositionId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.EXPOSITION_ID, expositionId);
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_LIST_EXPOSITION_EXPOSITION_IMAGE),
				new MapSqlParameterSource(params), getRowMapper());
	}
	
	private RowMapper<ListExpositionImage> getRowMapper() {
		return (rs, rowNum) -> ListExpositionImage.builder()
				.expositionId(rs.getLong(DaoConstant.EXPOSITION_ID))
				.expositionImageId(rs.getLong(DaoConstant.EXPOSITION_IMAGE_ID))
				.listExpositionImageId(rs.getLong(DaoConstant.LIST_EXPOSITION_IMAGE_ID))
				.build();
	}
	
	public void deleteListExpositionImageByExpositionId(Long idExposition) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.EXPOSITION_ID, idExposition);
		jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_LIST_EXPOSITION_EXPOSITION_IMAGE), new MapSqlParameterSource(params));
	}

	public int deleteListExpositionImageByExpositionImageId(Long idExpositionImage) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.EXPOSITION_IMAGE_ID, idExpositionImage);
		return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_LIST_EXPOSITION_EXPOSITION_IMAGE_ID), new MapSqlParameterSource(params));
	}

}
