package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.GroupedStatistic;
import org.agrisud.mediathequeapi.model.News;
import org.agrisud.mediathequeapi.model.StatiscticCountView;
import org.agrisud.mediathequeapi.model.StatisticData;
import org.agrisud.mediathequeapi.model.StatisticMedia;
import org.agrisud.mediathequeapi.model.Support;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/statistic_media.properties")
public class StatisticMediaDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	public void addStatisticMedia(StatisticMedia statisticMedia) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(environment.getProperty("insert_statistic_media"), getSqlParameterSource(statisticMedia));
//		return holder.getKey().longValue();
	}
	
	private MapSqlParameterSource getSqlParameterSource(StatisticMedia statisticMedia) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.STATISTIC_MEDIA_ID, statisticMedia.getStatisticMediaId())
				.addValue(DaoConstant.CATEGORY_ID, statisticMedia.getCategoryId())
				.addValue(DaoConstant.TYPE_CATEGORY, statisticMedia.getTypeCategory())
				.addValue(DaoConstant.MEDIA_ID, statisticMedia.getMediaId())
				.addValue(DaoConstant.ACTION, statisticMedia.getAction());
	}
	
	public Optional<StatiscticCountView> getCountStatisticBymediaId(Long categoryId,Long mediaId){
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, categoryId);
		params.put(DaoConstant.MEDIA_ID, mediaId);
		 try {
				Optional test = Optional.ofNullable(jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_COUNT_DOWNLOAD_VIEW),
						new MapSqlParameterSource(params), StatiscticCountView::baseMapper));
				return test;
			} catch (EmptyResultDataAccessException e) {
				return Optional.empty();
			}
	}
	
	private RowMapper<Map> getRowMapper() {
		Map<String, Integer> map = new HashMap<>();
		return (rs, rowNum) -> {
			map.get("number_view");
			map.get("number_download");
			return map;
		};
	}

	public Optional<StatisticData> getPercentage() {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, 1);
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_COUNT_MEDIA),
					new MapSqlParameterSource(params), StatisticData::baseMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<GroupedStatistic> getStatisticGroupByMounth(String year, String action, String typeCategory) {
		Map<String, Object> params = new HashMap<>();
		params.put("year", year);
		params.put("action", action);
		params.put("type_category", typeCategory);
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_STATISTIC_GROUP_BY_MONTH),new MapSqlParameterSource(params),GroupedStatistic::baseMapper );
	}

	public List<GroupedStatistic> getStatisticGroupByYear(String action, String typeCategory) {
		Map<String, Object> params = new HashMap<>();
		params.put("action", action);
		params.put("type_category", typeCategory);
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_STATISTIC_GROUP_BY_YEAR),new MapSqlParameterSource(params),GroupedStatistic::baseMapper );
	}

}
