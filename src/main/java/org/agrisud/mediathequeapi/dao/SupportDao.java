package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.Support;
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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@Repository
@PropertySource("classpath:sql/support.properties")
public class SupportDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	public Long addSupport(Support support) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_SUPPORT), getSqlParameterSource(support),holder);
		return holder.getKey().longValue();
	}
	
	private MapSqlParameterSource getSqlParameterSource(Support support) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.SUPPORT_ID, support.getSupportId())
				.addValue(DaoConstant.CATEGORY_ID, support.getCategoryId())
				.addValue(DaoConstant.TITLE, support.getTitle())
				.addValue(DaoConstant.PATH_SUPPORT, support.getPathSupport())
				.addValue(DaoConstant.PATH_IMAGE, support.getPathImage())
				.addValue(DaoConstant.URL_IMAGE, support.getUrlImage())
				.addValue(DaoConstant.URL_SUPPORT, support.getUrlSupport())
				.addValue(DaoConstant.LANGUAGE, support.getLanguage())
				.addValue(DaoConstant.DATE_SUPPORT, support.getDateSupport())
				.addValue(DaoConstant.DOCUMENT_TYPE_ID, support.getDocumentTypeId());
	}

	public List<Support> getListSupport(Long categoryId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, categoryId);
		 return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT),new MapSqlParameterSource(params), getRowMapper());
	}
	
	private RowMapper<Support> getRowMapper() {
		return (rs, rowNum) -> Support.builder()
				.supportId(rs.getLong(DaoConstant.SUPPORT_ID))
				.title(rs.getString(DaoConstant.TITLE))
				.pathSupport(rs.getString(DaoConstant.PATH_SUPPORT))
				.pathImage(rs.getString(DaoConstant.PATH_IMAGE))
				.urlImage(rs.getString(DaoConstant.URL_IMAGE))
				.urlSupport(rs.getString(DaoConstant.URL_SUPPORT))
				.dateSupport(rs.getString(DaoConstant.DATE_SUPPORT))
				.documentTypeId(rs.getLong(DaoConstant.DOCUMENT_TYPE_ID))
				.language(rs.getString(DaoConstant.LANGUAGE))
				.build();
	}

	public Support getSupportById(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, id);
		try {
			return jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_SUPPORT_BY_ID),
					new MapSqlParameterSource(params), getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void deleteSupport(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, id);
		jdbcTemplate.update(environment.getProperty(SqlConstant.DELET_SUPPORT_BY_ID), new MapSqlParameterSource(params));
	}

	public void updateSupport(Support support) {
		 jdbcTemplate.update(environment.getProperty(SqlConstant.UPDATE_SUPPORT),
				getSqlParameterSource(support));
	}

	public void deleteSupportByCategoryId(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, id);
		jdbcTemplate.update(environment.getProperty(SqlConstant.DELET_SUPPORT_BY_CATEGORY_ID), new MapSqlParameterSource(params));
	}
}
