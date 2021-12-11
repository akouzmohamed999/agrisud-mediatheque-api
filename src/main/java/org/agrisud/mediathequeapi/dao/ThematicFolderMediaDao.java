package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.ThematicFolder;
import org.agrisud.mediathequeapi.model.ThematicFolderMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/thematic_folder_media.properties")
public class ThematicFolderMediaDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;
	
	public void addThematicFolderMedia(ThematicFolderMedia thematicFolderMedia) {
		 jdbcTemplate.update(environment.getProperty("insert_thematic_folder_media"), getSqlParameterSource(thematicFolderMedia));
	}
	
	private MapSqlParameterSource getSqlParameterSource(ThematicFolderMedia thematicFolderMedia) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.THEMATIC_FOLDER_MEDIA_ID, thematicFolderMedia.getThematicFolderMediaId())
				.addValue(DaoConstant.CATEGORY_ID, thematicFolderMedia.getCategoryId())
				.addValue(DaoConstant.TYPE_CATEGORY, thematicFolderMedia.getTypeCategory())
				.addValue(DaoConstant.MEDIA_ID, thematicFolderMedia.getMediaId())
				.addValue(DaoConstant.PARENT_ID, thematicFolderMedia.getParentId());
	}

	public List<ThematicFolderMedia> getThematicFolderMediaByParentId(Long parentId) {
		Map<String, Object> params = new HashMap<>();
        params.put(DaoConstant.PARENT_ID, parentId);
		return jdbcTemplate.query(environment.getProperty("get_all_media_by_parent_id"),new MapSqlParameterSource(params) ,ThematicFolderMedia::baseMapper);
	}

	public void deleteThematicFolderById(Long thematicFolderMediaId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.THEMATIC_FOLDER_MEDIA_ID, thematicFolderMediaId);
		jdbcTemplate.update(environment.getProperty("delete_thematic_folder_media_by_id"), new MapSqlParameterSource(params));
	}
	
	public void deleteThematicFolderByParentId(Long parentId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.PARENT_ID, parentId);
		jdbcTemplate.update(environment.getProperty("delete_thematic_folder_by_parentId"), new MapSqlParameterSource(params));
	}
	public void deleteThematicFolderMediaByCategoryId(Long categoryId) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.CATEGORY_ID, categoryId);
		jdbcTemplate.update(environment.getProperty("delete_thematic_folder_media_by_catgeory_id"), new MapSqlParameterSource(params));
	}
}
