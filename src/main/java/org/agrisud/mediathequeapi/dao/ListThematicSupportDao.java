package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.agrisud.mediathequeapi.model.Thematic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/list_thematic_support.properties")
public class ListThematicSupportDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;
	
	public void addListThematicSupport(ListThematicSupport listThematicSupport) {
		jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_LIST_THEMATIC_SUPPORT), getSqlParameterSource(listThematicSupport));
	}
	
	private MapSqlParameterSource getSqlParameterSource(ListThematicSupport listThematicSupport) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.LIST_THEMATIC_SUPPORT_ID, listThematicSupport.getListThematicSupportId())
				.addValue(DaoConstant.THEMATIC_ID, listThematicSupport.getThematicId())
				.addValue(DaoConstant.SUPPORT_ID, listThematicSupport.getSupportId());
	}

	public List<ListThematicSupport> getListThematicBySupportId(Long supportId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, supportId);
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_THEMATIC_BY_SUPPORT_ID),
				new MapSqlParameterSource(params), getRowMapper());
	}
	
	private RowMapper<ListThematicSupport> getRowMapper() {
		return (rs, rowNum) -> ListThematicSupport.builder()
				.thematicId(rs.getLong(DaoConstant.THEMATIC_ID))
				.supportId(rs.getLong(DaoConstant.SUPPORT_ID))
				.listThematicSupportId(rs.getLong(DaoConstant.LIST_THEMATIC_SUPPORT_ID))
				.build();
	}

}
