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
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document(indexName = "mediatheque")
public class Support {
    @Id
    private String supportESId;
    @Field(type = FieldType.Long)
	private Long supportId;
	private Long categoryId;
	@Field(type = FieldType.Text)
    private String title;
    private String pathSupport;
    private String pathImage;
    private String urlImage;
    private String urlSupport;
    private Long documentTypeId;
    private String language;
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Country> listCountry;
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Thematic> listThematic;
    @Field(type = FieldType.Object)
    private DocumentType documentType;
    private String dateSupport;
    private boolean download;
    private Timestamp updateAt;


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
