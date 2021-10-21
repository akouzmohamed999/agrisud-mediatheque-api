package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.ListCountrySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
@PropertySource("classpath:sql/list_country_support_video.properties")
public class ListCountrySupportVideoDao {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	public void addListCountrySupportVideo(ListCountrySupport listCountrySupport) {
		jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_LIST_COUNTRY_SUPPORT_VIDEO), getSqlParameterSource(listCountrySupport));
	}

	public List<ListCountrySupport> getListCountryBySupportVideoId(Long supportId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, supportId);
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_LIST_COUNTY_BY_SUPPORT_VIDEO_ID),
				new MapSqlParameterSource(params), getRowMapper());
	}

	public void deleteListCounrtyBySupportVideoId(Long supportId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, supportId);
		jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_LIST_COUTRY_BY_SUPPORT_VIDEO_ID), new MapSqlParameterSource(params));
	}

	private MapSqlParameterSource getSqlParameterSource(ListCountrySupport listCountrySupport) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.LIST_COUNTRY_SUPPORT_ID, listCountrySupport.getListCountrySupportId())
				.addValue(DaoConstant.COUNTRY_ID, listCountrySupport.getCountryId())
				.addValue(DaoConstant.SUPPORT_ID, listCountrySupport.getSupportId());
	}

	private RowMapper<ListCountrySupport> getRowMapper() {
		return (rs, rowNum) -> ListCountrySupport.builder()
				.listCountrySupportId(rs.getLong(DaoConstant.LIST_COUNTRY_SUPPORT_ID))
				.supportId(rs.getLong(DaoConstant.SUPPORT_ID))
				.countryId(rs.getLong(DaoConstant.COUNTRY_ID))
				.build();
	}

}
