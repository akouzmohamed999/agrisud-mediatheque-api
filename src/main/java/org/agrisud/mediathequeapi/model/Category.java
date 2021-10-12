package org.agrisud.mediathequeapi.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Category {
	private Long categoryId;
    private String titleFrancais;
    private String titleAnglais;
    private String pathFolder;
    private String pathImage;
    private String urlImage;
    private String image;
    private Boolean lastLevel;
    private String typeCategory;

    public static Category baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        Category category = new Category();
        category.setCategoryId(resultSet.getLong("category_id"));
        category.setTitleAnglais(resultSet.getString("title_anglais"));
        category.setTitleFrancais(resultSet.getString("title_francais"));
        category.setPathFolder(resultSet.getString("path_folder"));
        category.setPathImage(resultSet.getString("path_image"));
        category.setUrlImage(resultSet.getString("url_image"));
        category.setLastLevel(resultSet.getBoolean("last_level"));
        category.setTypeCategory(resultSet.getString("type_category"));
        return category;
    }
}
