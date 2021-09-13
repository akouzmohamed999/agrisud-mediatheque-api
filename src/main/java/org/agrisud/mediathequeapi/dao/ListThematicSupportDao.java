package org.agrisud.mediathequeapi.dao;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.ListThematicSupport;
import org.agrisud.mediathequeapi.model.Thematic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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

}
