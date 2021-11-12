package org.agrisud.mediathequeapi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ThematicFolderMedia {
	private Long thematicFolderMediaId;
    private Long categoryId;
    private String typeCategory;
    private Long parentId;
    private Long mediaId;
    private Support support;
    private SupportVideo supportVideo;
    private Exposition exposition;
    
    public static ThematicFolderMedia baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        ThematicFolderMedia thematicFolderMedia= new ThematicFolderMedia();
        thematicFolderMedia.setThematicFolderMediaId(resultSet.getLong("thematic_folder_media_id"));
        thematicFolderMedia.setCategoryId(resultSet.getLong("category_id"));
        thematicFolderMedia.setTypeCategory(resultSet.getString("type_category"));
        thematicFolderMedia.setParentId(resultSet.getLong("parent_id"));
        thematicFolderMedia.setMediaId(resultSet.getLong("media_id"));
        return thematicFolderMedia;
    }
}
