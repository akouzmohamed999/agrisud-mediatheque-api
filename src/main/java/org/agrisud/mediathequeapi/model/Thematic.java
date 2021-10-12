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
public class Thematic {
	private Long thematicId;
	private String titleAnglais;
	private String titleFrancais;
	private String urlImage;
	private String pathImage;
	
	public static Thematic baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
		Thematic thematic = new Thematic();
		thematic.setThematicId(resultSet.getLong("thematic_id"));
		thematic.setTitleAnglais(resultSet.getString("title_anglais"));
		thematic.setTitleFrancais(resultSet.getString("title_francais"));
		thematic.setPathImage(resultSet.getString("path_image"));
		thematic.setUrlImage(resultSet.getString("url_image"));
        return thematic;
    }
}
