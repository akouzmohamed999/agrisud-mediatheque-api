package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.Category;
import org.agrisud.mediathequeapi.model.ThematicFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/thematic_folder.properties")
public class ThematicFolderDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;
	
	public void addThematicFolder(ThematicFolder thematicFolder) {
		 jdbcTemplate.update(environment.getProperty("insert_thematic_folder"), getSqlParameterSource(thematicFolder));
	}
	
	private MapSqlParameterSource getSqlParameterSource(ThematicFolder thematicFolder) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.THEMATIC_FOLDER_ID, thematicFolder.getThematicFolderId())
				.addValue(DaoConstant.TITLE_ANGLAIS, thematicFolder.getTitleAnglais())
				.addValue(DaoConstant.TITLE_FRANCAIS, thematicFolder.getTitleFrancais())
				.addValue(DaoConstant.PATH_FOLDER, thematicFolder.getPathFolder())
				.addValue(DaoConstant.PATH_IMAGE, thematicFolder.getPathImage())
				.addValue(DaoConstant.URL_IMAGE, thematicFolder.getUrlImage())
				.addValue(DaoConstant.PARENT_ID, thematicFolder.getParentId());
	}

	public List<ThematicFolder> getThematicFolderByParentId(Long parentId) {
		Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.PARENT_ID, parentId);
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_THEMATIC_FOLDER_BY_PARENT_ID),new MapSqlParameterSource(params) ,ThematicFolder::baseMapper);
	}

	public int updateThematicFolder(ThematicFolder thematicFolder) {
		return jdbcTemplate.update(environment.getProperty(SqlConstant.UPDATE_THEMATIC_FOLDER),
				getSqlParameterSource(thematicFolder));
	}

	public Optional<ThematicFolder> getThematicFolderById(Long id) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_THEMATIC_FOLDER_BY_ID), new MapSqlParameterSource(
				       "thematic_folder_id", id), ThematicFolder::baseMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public int deleteThematicFolder(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.THEMATIC_FOLDER_ID, id);
		return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_THEMATIC_FOLDER_BY_ID), new MapSqlParameterSource(params));
	}

	public List<ThematicFolder> getAllParent(Long parentId) {
		Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.PARENT_ID, parentId);
		return jdbcTemplate.query(environment.getProperty("get_all_parent"),new MapSqlParameterSource(params) ,ThematicFolder::baseMapper);
	}
}
