package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.GroupedStatistic;
import org.agrisud.mediathequeapi.model.StatisticVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/statistic_visitor.properties")
public class StatisticVisitorDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	public void addStatisticVisitor(StatisticVisitor statisticVisitor) {
		jdbcTemplate.update(environment.getProperty("insert_statistic_visitor_media"),getSqlParameterSource(statisticVisitor));
	}
	
	private MapSqlParameterSource getSqlParameterSource(StatisticVisitor statisticVisitor) {
		return new MapSqlParameterSource();
	}

	public List<GroupedStatistic> getStatisticGroupByMounth(String year) {
		Map<String, Object> params = new HashMap<>();
		params.put("year", year);
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_STATISTIC_VISITOR_GROUP_BY_MONTH),new MapSqlParameterSource(params),GroupedStatistic::baseMapper );
	}

	public List<GroupedStatistic> getStatisticGroupByYear() {
		Map<String, Object> params = new HashMap<>();
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_STATISTIC_VISITOR_GROUP_BY_YEAR),new MapSqlParameterSource(params),GroupedStatistic::baseMapper );
	}

}
