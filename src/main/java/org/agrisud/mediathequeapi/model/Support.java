package org.agrisud.mediathequeapi.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document(indexName = "support")
@Setting(settingPath = "es/settings.json")
public class Support {
    @Id
    private String supportESId;
    @Field(type = FieldType.Long)
    private Long supportId;
    private Long categoryId;
    @Field(type = FieldType.Text, analyzer = "ngram_analyzer", fielddata = true)
    private String title;
    private String pathSupport;
    private String pathImage;
    private String urlImage;
    private String urlSupport;
    private Long documentTypeId;
    @Field(type = FieldType.Text, fielddata = true)
    private String language;
    @Field(type = FieldType.Nested, includeInParent = true, fielddata = true)
    private List<Country> listCountry;
    @Field(type = FieldType.Nested, includeInParent = true, fielddata = true)
    private List<Thematic> listThematic;
    @Field(type = FieldType.Object)
    private DocumentType documentType;
    @Field( type = FieldType.Text,fielddata = true)
    private String dateSupport;
    private boolean download;
    @Field(type = FieldType.Text)
    private Timestamp updateAt;
    private Integer numberView;
    private Integer numberDownload;


    public static Support baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        Support support = new Support();
        support.setSupportId(resultSet.getLong("support_id"));
        support.setCategoryId(resultSet.getLong("category_id"));
        support.setTitle(resultSet.getString("title"));
        support.setPathSupport(resultSet.getString("path_support"));
        support.setPathImage(resultSet.getString("path_image"));
        support.setUrlImage(resultSet.getString("url_image"));
        support.setUrlSupport(resultSet.getString("url_support"));
        support.setDocumentTypeId(resultSet.getLong("document_type_id"));
        support.setLanguage(resultSet.getString("language"));
        support.setListCountry(new ArrayList<>());
        support.setListThematic(new ArrayList<>());
        support.setDateSupport(resultSet.getString("date_support"));
        support.setDownload(resultSet.getBoolean("download"));
        return support;
    }

}
