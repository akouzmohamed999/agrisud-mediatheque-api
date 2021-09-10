package org.agrisud.mediathequeapi.dao;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
//@PropertySource("classpath:sql/country.properties")
public class CountryDao {
//	@Autowired
//    NamedParameterJdbcTemplate jdbcTemplate;
//
//	@Autowired
//	Environment environment;
//		
//	public int addCountry(Country country) {
//		return jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_COUNTRY), getSqlParameterSource(country));
//	}
//	
//	private MapSqlParameterSource getSqlParameterSource(Coutry thema) {
//		return new MapSqlParameterSource()
//				.addValue(DaoConstant.CATEGORY_ID, category.getCategoryId())
//				.addValue(DaoConstant.TITLE, category.getTitle())
//				.addValue(DaoConstant.PATH_FOLDER, category.getPathFolder())
//				.addValue(DaoConstant.PATH_IMAGE, category.getPathImage())
//				.addValue(DaoConstant.URL_IMAGE, category.getUrlImage())
//				.addValue(DaoConstant.LAST_LEVEL, category.getLastLevel());
//	}
}
