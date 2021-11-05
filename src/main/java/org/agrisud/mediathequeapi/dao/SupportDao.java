package org.agrisud.mediathequeapi.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.enums.SortColumn;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.Support;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Repository
@PropertySource("classpath:sql/support.properties")
public class SupportDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	public Long addSupport(Support support) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_SUPPORT), getSqlParameterSource(support),holder);
		return holder.getKey().longValue();
	}
	
	private MapSqlParameterSource getSqlParameterSource(Support support) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.SUPPORT_ID, support.getSupportId())
				.addValue(DaoConstant.CATEGORY_ID, support.getCategoryId())
				.addValue(DaoConstant.TITLE, support.getTitle())
				.addValue(DaoConstant.PATH_SUPPORT, support.getPathSupport())
				.addValue(DaoConstant.PATH_IMAGE, support.getPathImage())
				.addValue(DaoConstant.URL_IMAGE, support.getUrlImage())
				.addValue(DaoConstant.URL_SUPPORT, support.getUrlSupport())
				.addValue(DaoConstant.LANGUAGE, support.getLanguage())
				.addValue(DaoConstant.DATE_SUPPORT, support.getDateSupport())
				.addValue(DaoConstant.DOWNLOAD, support.isDownload())
				.addValue(DaoConstant.DOCUMENT_TYPE_ID, support.getDocumentTypeId());
	}

	public Page<Support> getListSupport(Long categoryId,Pageable pageable) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, categoryId);
		params.put("limit", pageable.getPageSize());
		params.put("offset" , pageable.getOffset());
		 Optional<Integer> total = Optional.ofNullable(jdbcTemplate
	                .queryForObject(environment.getProperty("support.get.all.by_categoryid.per.page.count"), new MapSqlParameterSource(params), Integer.class));

		 List<Support> listSupport =  jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT),new MapSqlParameterSource(params), getRowMapper());
		 return new PageImpl<>(listSupport, pageable, total.get());
	}
	
	public List<Support> getListAllSupport(Long categoryId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, categoryId);
		 return jdbcTemplate.query(environment.getProperty("select_all_support_by_category_id"),new MapSqlParameterSource(params), getRowMapper());
	}
	
	private RowMapper<Support> getRowMapper() {
		return (rs, rowNum) -> Support.builder()
				.supportId(rs.getLong(DaoConstant.SUPPORT_ID))
				.title(rs.getString(DaoConstant.TITLE))
				.pathSupport(rs.getString(DaoConstant.PATH_SUPPORT))
				.pathImage(rs.getString(DaoConstant.PATH_IMAGE))
				.urlImage(rs.getString(DaoConstant.URL_IMAGE))
				.urlSupport(rs.getString(DaoConstant.URL_SUPPORT))
				.dateSupport(rs.getString(DaoConstant.DATE_SUPPORT))
				.documentTypeId(rs.getLong(DaoConstant.DOCUMENT_TYPE_ID))
				.language(rs.getString(DaoConstant.LANGUAGE))
				.download(rs.getBoolean(DaoConstant.DOWNLOAD))
				.updateAt(rs.getTimestamp("updated_at"))
				.build();
	}

	public Optional<Support> getSupportById(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, id);
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_SUPPORT_BY_ID),
					new MapSqlParameterSource(params), getRowMapper()));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
		 
	}

	public void deleteSupport(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.SUPPORT_ID, id);
		jdbcTemplate.update(environment.getProperty(SqlConstant.DELET_SUPPORT_BY_ID), new MapSqlParameterSource(params));
	}

	public void updateSupport(Support support) {
		 jdbcTemplate.update(environment.getProperty(SqlConstant.UPDATE_SUPPORT),
				getSqlParameterSource(support));
	}

	public void deleteSupportByCategoryId(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, id);
		jdbcTemplate.update(environment.getProperty(SqlConstant.DELET_SUPPORT_BY_CATEGORY_ID), new MapSqlParameterSource(params));
	}
	
	public List<Support> getSupportByOrderASC(Long categoryId,SortColumn sortColumn,String language) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, categoryId);
		
        switch (sortColumn) {
            case SUPPORT_TITLE -> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_ORDER_BY_TITLE_ASC),new MapSqlParameterSource(params), getRowMapper());
            }
            case SUPPORT_DATE -> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_ORDER_BY_DATE_ASC),new MapSqlParameterSource(params), getRowMapper());
            }
            case SUPPORT_DOCUMENT_TYPE-> {
            	if("en".equals(language)) {
            		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_ORDER_BY_DOCUMENT_TYPE_ANGLAIS_ASC),new MapSqlParameterSource(params), getRowMapper());
            	}else {
            		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_ORDER_BY_DOCUMENT_TYPE_FRANCAIS_ASC),new MapSqlParameterSource(params), getRowMapper());
            	}
            	
            }
            case SUPPORT_LANGUE-> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_ORDER_BY_LANGUE_ASC),new MapSqlParameterSource(params), getRowMapper());
            }
            default -> {
            	return new ArrayList<Support>();
            }
        }

	}

	public List<Support> getSupportByOrderDESC(Long categoryId,SortColumn sortColumn,String language) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, categoryId);
		
        switch (sortColumn) {
            case SUPPORT_TITLE -> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_ORDER_BY_TITLE_DESC),new MapSqlParameterSource(params), getRowMapper());
            }
            case SUPPORT_DATE -> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_ORDER_BY_DATE_DESC),new MapSqlParameterSource(params), getRowMapper());
            }
            case SUPPORT_DOCUMENT_TYPE-> {
            	if("en".equals(language)) {
            		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_ORDER_BY_DOCUMENT_TYPE_ANGLAIS_DESC),new MapSqlParameterSource(params), getRowMapper());
            	}else {
            		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_ORDER_BY_DOCUMENT_TYPE_FRANCAIS_DESC),new MapSqlParameterSource(params), getRowMapper());
            	}
            	
            }
            case SUPPORT_LANGUE-> {
            	return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_SUPPORT_ORDER_BY_LANGUE_DESC),new MapSqlParameterSource(params), getRowMapper());
            }
            default -> {
            	return new ArrayList<Support>();
            }
        }
	}

	public Support getLastNews() {
		try {
			return jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_SUPPORT_LAST_NEWS),
					new MapSqlParameterSource(), getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
