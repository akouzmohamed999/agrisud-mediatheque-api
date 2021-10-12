package org.agrisud.mediathequeapi.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
public class SupportVideo {
	private Long supportId;
	private Long categoryId;
    private String title;
    private String pathSupport;
    private String urlSupport;
    private Long videoTypeId;
    private String language;
    private List<Country> listCountry;
    private List<Thematic> listThematic;
    private VideoType videoType;
    private String dateSupport;
    private String link;
    
    public static SupportVideo baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        SupportVideo support = new SupportVideo();
        support.setSupportId(resultSet.getLong("support_id"));
        support.setCategoryId(resultSet.getLong("category_id"));
        support.setTitle(resultSet.getString("title"));
        support.setPathSupport(resultSet.getString("path_support"));
        support.setUrlSupport(resultSet.getString("url_support"));
        support.setVideoTypeId(resultSet.getLong("video_type_id"));
        support.setLanguage(resultSet.getString("language"));
        support.setListCountry(new ArrayList<>());
        support.setListThematic(new ArrayList<>());
        support.setDateSupport(resultSet.getString("date_support"));
        support.setLink(resultSet.getString("link"));
        return support;
    }
    
}
