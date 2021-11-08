package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/category.properties")
public class CategoryDao {
	 @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;

	public void addCategory(Category category) {
		 jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_CATEGORY), getSqlParameterSource(category));
	}
	
	private MapSqlParameterSource getSqlParameterSource(Category category) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.CATEGORY_ID, category.getCategoryId())
				.addValue(DaoConstant.TITLE_ANGLAIS, category.getTitleAnglais())
				.addValue(DaoConstant.TITLE_FRANCAIS, category.getTitleFrancais())
				.addValue(DaoConstant.PATH_FOLDER, category.getPathFolder())
				.addValue(DaoConstant.PATH_IMAGE, category.getPathImage())
				.addValue(DaoConstant.URL_IMAGE, category.getUrlImage())
				.addValue(DaoConstant.TYPE_CATEGORY, category.getTypeCategory())
				.addValue(DaoConstant.LAST_LEVEL, category.getLastLevel());
	}

	public List<Category> getAllCategory() {
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_ALL_CATEGORY), Category::baseMapper);
	}

	public Optional<Category> getCategoryById(Long id) {
		 try {
				return Optional.ofNullable(jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_CATEGORY_BY_ID), new MapSqlParameterSource(
					       "category_id", id), getRowMapper()));
			} catch (EmptyResultDataAccessException e) {
				return Optional.empty();
			}
	}
	private RowMapper<Category> getRowMapper() {
		return (rs, rowNum) -> Category.builder()
				.categoryId(rs.getLong(DaoConstant.CATEGORY_ID))
				.titleFrancais(rs.getString(DaoConstant.TITLE_FRANCAIS))
				.titleAnglais(rs.getString(DaoConstant.TITLE_ANGLAIS))
				.pathFolder(rs.getString(DaoConstant.PATH_FOLDER))
				.pathImage(rs.getString(DaoConstant.PATH_IMAGE))
				.urlImage(rs.getString(DaoConstant.URL_IMAGE))
				.lastLevel(rs.getBoolean(DaoConstant.LAST_LEVEL))
				.typeCategory(rs.getString(DaoConstant.TYPE_CATEGORY))
				.build();
	}

	public int deleteCategory(String pathFolder, Long categoryId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.PATH_FOLDER, "%" + pathFolder +"/"+ "%");
		params.put(DaoConstant.CATEGORY_ID, categoryId);
		return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_CATEGORY_BY_ID), new MapSqlParameterSource(params));
	}

	public List<Category> getCategoryByTitle(String title,String path ) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.TITLE, "%" + title + "%");
		params.put(DaoConstant.PATH_FOLDER, path);
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_CATEGORY_BY_TILTLE),
									new MapSqlParameterSource(params), getRowMapper());
	}

	public List<Category> checkTitleIfExist(String pathFolder, String title,String type) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.TITLE,  title );
		if("francais".equals(type)) {
			return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_CATEGORY_CHECK_TILTLE_FRANCAIS),
					new MapSqlParameterSource(params), getRowMapper());
		}
		else {
			return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_CATEGORY_CHECK_TILTLE_ANGLAIS),
					new MapSqlParameterSource(params), getRowMapper());
		}
		
	}

	public List<Category> getChildOfLevel(String pathFolder) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.PATH_FOLDER, pathFolder);
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_CATEGORY_CHILD_OF_LEVEL),
				new MapSqlParameterSource(params), getRowMapper());
	}

	public int updateCategory(Category category) {
		return jdbcTemplate.update(environment.getProperty(SqlConstant.UPDATE_CATEGORY),
				getSqlParameterSource(category));
	}
}
