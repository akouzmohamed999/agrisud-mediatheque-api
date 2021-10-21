package org.agrisud.mediathequeapi.model;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ListThematicExposition {
	private Long listThematicExpositionId;
    private Long thematicId;
    private Long expositionId;
    
    public static ListThematicExposition baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
    	ListThematicExposition listThematicSupport = new ListThematicExposition();
    	listThematicSupport.setListThematicExpositionId(resultSet.getLong("list_thematic_exposition_id"));
    	listThematicSupport.setThematicId(resultSet.getLong("thematic_id"));
    	listThematicSupport.setExpositionId(resultSet.getLong("exposition_id"));
        return listThematicSupport;
    }
}
