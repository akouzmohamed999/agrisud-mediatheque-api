package org.agrisud.mediathequeapi.dao;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.ExpositionImage;
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
@PropertySource("classpath:sql/exposition_image.properties")
public class ExpositionImageDao {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    Environment environment;

    public Long addExpositionImage(ExpositionImage expositionImage) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_EXPOSITION_IMAGE), getSqlParameterSource(expositionImage), holder);
        return holder.getKey().longValue();
    }

    private MapSqlParameterSource getSqlParameterSource(ExpositionImage expositionImage) {
        return new MapSqlParameterSource()
                .addValue(DaoConstant.EXPOSITION_IMAGE_ID, expositionImage.getExpositionImageId())
                .addValue(DaoConstant.NAME, expositionImage.getName())
                .addValue(DaoConstant.URL_IMAGE, expositionImage.getUrlImage())
                .addValue(DaoConstant.PATH_IMAGE, expositionImage.getPathImage())
                .addValue(DaoConstant.RANK, expositionImage.getRankImg());
    }

    public List<ExpositionImage> getListExpositionImage() {
        return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_EXPOSITION_IMAGE_BY_ID), ExpositionImage::baseMapper);
    }

    public List<ExpositionImage> getListExpositionImageByExpositionId(Long expositionId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.EXPOSITION_ID, expositionId);
        return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_LIST_EXPOSITION_EXPOSITION_IMAGE), new MapSqlParameterSource(params), getRowMapper());
    }

    public ExpositionImage getExpositionImageById(Long expositionImageId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.EXPOSITION_IMAGE_ID, expositionImageId);
        try {
            return jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_EXPOSITION_IMAGE_BY_ID),
                    new MapSqlParameterSource(params), getRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int deleteExpositionImage(Long expositionImageId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.EXPOSITION_IMAGE_ID, expositionImageId);
        return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_EXPOSITION_IMAGE), new MapSqlParameterSource(params));
    }

    public int deleteExpositionImageById(Long expositionImageId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.EXPOSITION_IMAGE_ID, expositionImageId);
        return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_EXPOSITION_IMAGE_BY_ID), new MapSqlParameterSource(params));
    }


    public int deleteListExpositionImageByExpositionImageId(Long expositionImageId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.EXPOSITION_IMAGE_ID, expositionImageId);
        return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_LIST_EXPOSITION_EXPOSITION_IMAGE_ID), new MapSqlParameterSource(params));
    }

    public int deleteExpositionImageByIdExposition(Long expositionId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.EXPOSITION_ID, expositionId);
        return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_EXPOSITION_IMAGE), new MapSqlParameterSource(params));
    }

    public int updateExpositionImage(Long expositionImageId, int rank) {
        Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.EXPOSITION_IMAGE_ID, expositionImageId);
        params.put(DaoConstant.RANK, rank);
        return jdbcTemplate.update(environment.getProperty(SqlConstant.UPDATE_RANK_EXPOSITION_IMAGE_BY_ID), new MapSqlParameterSource(params));
    }

    private RowMapper<ExpositionImage> getRowMapper() {
        return (rs, rowNum) -> ExpositionImage.builder()
                .expositionImageId(rs.getLong(DaoConstant.EXPOSITION_IMAGE_ID))
                .name(rs.getString(DaoConstant.NAME))
                .urlImage(rs.getString(DaoConstant.URL_IMAGE))
                .pathImage(rs.getString(DaoConstant.PATH_IMAGE))
                .rankImg(rs.getLong(DaoConstant.RANK))
                .build();
    }
}
