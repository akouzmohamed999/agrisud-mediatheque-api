package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.Country;
import org.agrisud.mediathequeapi.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/country.properties")
public class CountryDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	public int addCountry(Country country) {
		 return jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_COUNTRY), getSqlParameterSource(country));
	}
	
	private MapSqlParameterSource getSqlParameterSource(Country country) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.COUNTRY_ID, country.getCountryId())
				.addValue(DaoConstant.TITLE_ANGLAIS, country.getTitleAnglais())
				.addValue(DaoConstant.TITLE_FRANCAIS, country.getTitleFrancais())
				.addValue(DaoConstant.PATH_IMAGE, country.getPathImage())
				.addValue(DaoConstant.URL_IMAGE, country.getUrlImage());
	}

	public int deleteCountry(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.COUNTRY_ID, id);
		return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_COUNTRY_BY_ID), new MapSqlParameterSource(params));
	}

	public int updateCountry(Country country) {
		return jdbcTemplate.update(environment.getProperty(SqlConstant.UPDATE_COUNTRY),
				getSqlParameterSource(country));
	}

	public List<Country> getAllCountry() {
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_ALL_COUNTRY), Country::baseMapper);
	}

	public Country getCountryById(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.COUNTRY_ID, id);
		try {
			return jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_COUNTRY_BY_ID),
					new MapSqlParameterSource(params), getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	private RowMapper<Country> getRowMapper() {
		return (rs, rowNum) -> Country.builder()
				.countryId(rs.getLong(DaoConstant.COUNTRY_ID))
				.titleAnglais(rs.getString(DaoConstant.TITLE_ANGLAIS))
				.titleFrancais(rs.getString(DaoConstant.TITLE_FRANCAIS))
				.pathImage(rs.getString(DaoConstant.PATH_IMAGE))
				.urlImage(rs.getString(DaoConstant.URL_IMAGE))
				.build();
	}
}
