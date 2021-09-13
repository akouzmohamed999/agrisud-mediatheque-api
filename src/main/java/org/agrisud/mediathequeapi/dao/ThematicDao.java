package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.Thematic;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
@PropertySource("classpath:sql/thematic.properties")
public class ThematicDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	public int addThematic(Thematic thematic) {
		 return jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_THEMATIC), getSqlParameterSource(thematic));
	}
	
	private MapSqlParameterSource getSqlParameterSource(Thematic thematic) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.THEMATIC_ID, thematic.getThematicId())
				.addValue(DaoConstant.TITLE, thematic.getTitle());
	}

	public int deleteThematic(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.THEMATIC_ID, id);
		return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_THEMATIC_BY_ID), new MapSqlParameterSource(params));
	}

	public int updateThematic(Thematic thematic) {
		return jdbcTemplate.update(environment.getProperty(SqlConstant.UPDATE_THEMATIC),
				getSqlParameterSource(thematic));
	}

	public List<Thematic> getAllThematic() {
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_ALL_THEMATIC), Thematic::baseMapper);
	}

	public Thematic getThematicById(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.THEMATIC_ID, id);
		try {
			return jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_THEMATIC_BY_ID),
					new MapSqlParameterSource(params), getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	private RowMapper<Thematic> getRowMapper() {
		return (rs, rowNum) -> Thematic.builder()
				.thematicId(rs.getLong(DaoConstant.THEMATIC_ID))
				.title(rs.getString(DaoConstant.TITLE))
				.build();
	}
	
}
