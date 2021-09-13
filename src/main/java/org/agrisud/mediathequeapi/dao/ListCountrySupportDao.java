package org.agrisud.mediathequeapi.dao;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.ListCountrySupport;
import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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

}
