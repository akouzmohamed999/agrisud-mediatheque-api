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
public class VideoType {
	private Long videoTypeId;
    private String title;
    
    public static VideoType baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
    	VideoType videoType = new VideoType();
    	videoType.setVideoTypeId(resultSet.getLong("video_type_id"));
    	videoType.setTitle(resultSet.getString("title"));
        return videoType ;
    }
}
