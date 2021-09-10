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
	private String title;
	
	public static Thematic baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
		Thematic thematic = new Thematic();
		thematic.setThematicId(resultSet.getLong("thematic_id"));
		thematic.setTitle(resultSet.getString("title"));
        return thematic;
    }
}
