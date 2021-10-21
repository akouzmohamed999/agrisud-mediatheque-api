package org.agrisud.mediathequeapi.dao;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.ListThematicExposition;
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
@PropertySource("classpath:sql/list_thematic_exposition.properties")
public class ListThematicExpositionDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;
	
	public void addListThematicExposition(ListThematicExposition listThematicExposition) {
		jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_LIST_THEMATIC_EXPOSITION), getSqlParameterSource(listThematicExposition));
	}
	
	private MapSqlParameterSource getSqlParameterSource(ListThematicExposition listThematicExposition) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.EXPOSITION_ID, listThematicExposition.getExpositionId())
				.addValue(DaoConstant.THEMATIC_ID, listThematicExposition.getThematicId())
				.addValue(DaoConstant.LIST_THEMATIC_EXPOSITION_ID, listThematicExposition.getListThematicExpositionId());
	}

	public List<ListThematicExposition> getListExpositionByThematicId(Long thematicId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.THEMATIC_ID, thematicId);
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_LIST_THEMATIC_EXPOSITION_BY_THEMATIC_ID),
				new MapSqlParameterSource(params), getRowMapper());
	}
	
	private RowMapper<ListThematicExposition> getRowMapper() {
		return (rs, rowNum) -> ListThematicExposition.builder()
				.thematicId(rs.getLong(DaoConstant.THEMATIC_ID))
				.expositionId(rs.getLong(DaoConstant.EXPOSITION_ID))
				.listThematicExpositionId(rs.getLong(DaoConstant.LIST_THEMATIC_EXPOSITION_ID))
				.build();
	}
	
	public void deleteListThematicByExpositionId(Long expositionId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.EXPOSITION_ID, expositionId);
		jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_LIST_THEMATIC_EXPOSITION), new MapSqlParameterSource(params));
	}

}
