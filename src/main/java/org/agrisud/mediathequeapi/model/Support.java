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
public class Support {
	private Long supportId;
    private String title;
    private String pathSupport;
    private String pathImage;
    private String urlImage;
    private String urlSupport;
    private Long documentTypeId;
    private String language;
    private List<String> listCountry;
    private List<Thematic> listThematic;
    private String dateSupport;
    
    public static Support baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        Support support = new Support();
        support.setSupportId(resultSet.getLong("support_id"));
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
        return support;
    }
    
}
