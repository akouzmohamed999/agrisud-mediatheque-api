package org.agrisud.mediathequeapi.dao;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.Exposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@PropertySource("classpath:sql/exposition.properties")
public class ExpositionDao {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    Environment environment;

    public Long addExposition(Exposition exposition) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_EXPOSITION), getSqlParameterSource(exposition), holder);
        return holder.getKey().longValue();
    }

    public int updateExposition(Exposition exposition) {
        return jdbcTemplate.update(environment.getProperty(SqlConstant.UPDATE_EXPOSITION), getSqlParameterSource(exposition));
    }


    private MapSqlParameterSource getSqlParameterSource(Exposition exposition) {
        return new MapSqlParameterSource()
                .addValue(DaoConstant.EXPOSITION_ID, exposition.getExpositionId())
                .addValue(DaoConstant.CATEGORY_ID, exposition.getCategoryId())
                .addValue(DaoConstant.TITLE_FR, exposition.getTitleFr())
                .addValue(DaoConstant.TITLE_EN, exposition.getTitleEn())
                .addValue(DaoConstant.DATE_EXPOSITION, exposition.getDateExposition())
                .addValue(DaoConstant.COUNTRY_ID, exposition.getCountryId())
                .addValue(DaoConstant.DESCRIPTION_FR, exposition.getDescriptionFr())
                .addValue(DaoConstant.DESCRIPTION_EN, exposition.getDescriptionEn());
    }

    public List<Exposition> getListExpositionByCategory(Long categoryId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.CATEGORY_ID, categoryId);
        return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_EXPOSITION_ALL_BY_CATEGORY_ID), new MapSqlParameterSource(params), getRowMapper());
    }

    private RowMapper<Exposition> getRowMapper() {
        return (rs, rowNum) -> Exposition.builder()
                .expositionId(rs.getLong(DaoConstant.EXPOSITION_ID))
                .categoryId(rs.getLong(DaoConstant.CATEGORY_ID))
                .titleFr(rs.getString(DaoConstant.TITLE_FR))
                .titleEn(rs.getString(DaoConstant.TITLE_EN))
                .dateExposition(rs.getString(DaoConstant.DATE_EXPOSITION))
                .countryId(rs.getLong(DaoConstant.COUNTRY_ID))
                .descriptionEn(rs.getString(DaoConstant.DESCRIPTION_EN))
                .descriptionFr(rs.getString(DaoConstant.DESCRIPTION_FR))
                .updateAt(rs.getTimestamp("updated_at"))
                .build();
    }

    public Exposition getExpositionById(Long expositionId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.EXPOSITION_ID, expositionId);
        try {
            return jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_EXPOSITION_BY_ID),
                    new MapSqlParameterSource(params), getRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int deleteExposition(Long expositionId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.EXPOSITION_ID, expositionId);
        return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_EXPOSITION), new MapSqlParameterSource(params));
    }

	public Exposition getLastNews() {
		try {
			return jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_EXPOSITION_LAST_NEWS),
					new MapSqlParameterSource(), getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


}
