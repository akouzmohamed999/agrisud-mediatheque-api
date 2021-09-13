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
public class ListThematicSupport {
	private Long listThematicSupportId;
    private Long thematicId;
    private Long supportId;
    
    public static ListThematicSupport baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
    	ListThematicSupport listThematicSupport = new ListThematicSupport();
    	listThematicSupport.setListThematicSupportId(resultSet.getLong("list_thematic_support_id"));
    	listThematicSupport.setThematicId(resultSet.getLong("thematic_id"));
    	listThematicSupport.setSupportId(resultSet.getLong("support_id"));
        return listThematicSupport;
    }
}
