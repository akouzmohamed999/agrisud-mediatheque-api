package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.ListCountrySupport;
import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/list_country_support.properties")
public class ListCountrySupportDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;
	
	public void addListCountrySupport(ListCountrySupport listCountrySupport) {
		jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_LIST_COUNTRY_SUPPORT), getSqlParameterSource(listCountrySupport));
	}
	
	private MapSqlParameterSource getSqlParameterSource(ListCountrySupport listCountrySupport) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.LIST_COUNTRY_SUPPORT_ID, listCountrySupport.getListCountrySupportId())
				.addValue(DaoConstant.COUNTRY_CODE, listCountrySupport.getCountryCode())
				.addValue(DaoConstant.SUPPORT_ID, listCountrySupport.getSupportId());
	}

	public List<String> getListCountryBySupportId(Long supportId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, supportId);
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_LIST_COUNTY_BY_SUPPORT_ID),
				new MapSqlParameterSource(params), getRowMapper());
	}
	
	private RowMapper<String> getRowMapper() {
		return (rs, rowNum) -> 
				rs.getString(DaoConstant.COUNTRY_CODE);
				
	}

}
