package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.News;
import org.agrisud.mediathequeapi.model.Support;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/news.properties")
public class NewsDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	public int getCountNews() {
		int total = jdbcTemplate
                .queryForObject(environment.getProperty("select_count"), new MapSqlParameterSource(), Integer.class);
		return total;
	}

	public void addNews(News news) {
		KeyHolder holder = new GeneratedKeyHolder();
		deleteNewsBySupportId(news.getSupportId());
		jdbcTemplate.update(environment.getProperty("insert_news"), getSqlParameterSource(news),holder);
	}
	
	private MapSqlParameterSource getSqlParameterSource(News news) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.SUPPORT_ID, news.getSupportId())
				.addValue(DaoConstant.NEWS_ID, news.getNewsId())
				.addValue(DaoConstant.TYPE_CATEGORY, news.getTypeCategory());
	}

	public void deleteFirstNews() {
		jdbcTemplate.update(environment.getProperty("delete_first_news"), new MapSqlParameterSource());
	}

	public void deleteNewsBySupportId(Long supportId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, supportId);
		jdbcTemplate.update(environment.getProperty("delete_news_by_support_id"), new MapSqlParameterSource(params));
	}

	public List<News> getListNews() {
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_ALL_NEWS), News::baseMapper);
	}
}
