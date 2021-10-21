package org.agrisud.mediathequeapi.model;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ListExpositionImage {
	private Long listExpositionImageId;
    private Long expositionId;
    private Long expositionImageId;
    
    public static ListExpositionImage baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
    	ListExpositionImage listExpositionImage = new ListExpositionImage();
        listExpositionImage.setListExpositionImageId(resultSet.getLong("list_exposition_image_id"));
        listExpositionImage.setExpositionId(resultSet.getLong("exposition_id"));
        listExpositionImage.setExpositionImageId(resultSet.getLong("exposition_image_id"));
        return listExpositionImage;
    }
}
