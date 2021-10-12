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
	private String titleAnglais;
	private String titleFrancais;
	private String urlImage;
	private String pathImage;
    
    public static VideoType baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
    	VideoType videoType = new VideoType();
    	videoType.setVideoTypeId(resultSet.getLong("video_type_id"));
    	videoType.setTitleAnglais(resultSet.getString("title_anglais"));
    	videoType.setTitleFrancais(resultSet.getString("title_francais"));
    	videoType.setPathImage(resultSet.getString("path_image"));
    	videoType.setUrlImage(resultSet.getString("url_image"));
        return videoType ;
    }
}
