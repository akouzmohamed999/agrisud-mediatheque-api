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
    private String title;
    private String pathFolder;
    private String pathImage;
    private String urlImage;
    private String image;
    private Boolean lastLevel;

    public static Category baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        Category category = new Category();
        category.setCategoryId(resultSet.getLong("category_id"));
        category.setTitle(resultSet.getString("title"));
        category.setPathFolder(resultSet.getString("path_folder"));
        category.setPathImage(resultSet.getString("path_image"));
        category.setUrlImage(resultSet.getString("url_image"));
        category.setLastLevel(resultSet.getBoolean("last_level"));
        return category;
    }
}
