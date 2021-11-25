package org.agrisud.mediathequeapi.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

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
@Document(indexName = "video")
@Setting(settingPath = "es/settings.json")
public class SupportVideo {
	@Id
    private String supportVideoESId;
	@Field(type = FieldType.Long)
    private Long supportId;
    private Long categoryId;
    @Field(type = FieldType.Text, analyzer = "ngram_analyzer")
    private String title;
    private String pathSupport;
    private String urlSupport;
    private Long videoTypeId;
    private String language;
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Country> listCountry;
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Thematic> listThematic;
    @Field(type = FieldType.Object)
    private VideoType videoType;
    private String dateSupport;
    private String link;
    @Field(type = FieldType.Text)
    private Timestamp updateAt;
    private Integer numberView;
    private Integer numberDownload;

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
