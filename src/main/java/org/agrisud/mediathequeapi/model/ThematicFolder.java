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
public class ThematicFolder {
	private Long thematicFolderId;
    private String titleFrancais;
    private String titleAnglais;
    private String pathFolder;
    private String pathImage;
    private String urlImage;
    private Long parentId;

    public static ThematicFolder baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        ThematicFolder thematicFolder= new ThematicFolder();
        thematicFolder.setThematicFolderId(resultSet.getLong("thematic_folder_id"));
        thematicFolder.setTitleAnglais(resultSet.getString("title_anglais"));
        thematicFolder.setTitleFrancais(resultSet.getString("title_francais"));
        thematicFolder.setPathFolder(resultSet.getString("path_folder"));
        thematicFolder.setPathImage(resultSet.getString("path_image"));
        thematicFolder.setUrlImage(resultSet.getString("url_image"));
        thematicFolder.setParentId(resultSet.getLong("parent_id"));
        return thematicFolder;
    }
}
