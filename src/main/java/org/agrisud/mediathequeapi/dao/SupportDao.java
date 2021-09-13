package org.agrisud.mediathequeapi.dao;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.Support;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
				.addValue(DaoConstant.TITLE, support.getTitle())
				.addValue(DaoConstant.PATH_SUPPORT, support.getPathSupport())
				.addValue(DaoConstant.PATH_IMAGE, support.getPathImage())
				.addValue(DaoConstant.URL_IMAGE, support.getUrlImage())
				.addValue(DaoConstant.URL_SUPPORT, support.getUrlSupport())
				.addValue(DaoConstant.LANGUAGE, support.getLanguage())
				.addValue(DaoConstant.DATE_SUPPORT, support.getDateSupport())
				.addValue(DaoConstant.DOCUMENT_TYPE_ID, support.getDocumentTypeId());
	}
}
