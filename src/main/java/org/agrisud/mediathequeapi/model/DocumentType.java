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
public class DocumentType {
	private Long documentTypeId;
	private String titleAnglais;
	private String titleFrancais;
	private String urlImage;
	private String pathImage;
    
    public static DocumentType baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
    	DocumentType documentType = new DocumentType();
    	documentType .setDocumentTypeId(resultSet.getLong("document_type_id"));
    	documentType .setTitleAnglais(resultSet.getString("title_anglais"));
    	documentType .setTitleFrancais(resultSet.getString("title_francais"));
    	documentType .setUrlImage(resultSet.getString("url_image"));
    	documentType .setPathImage(resultSet.getString("path_image"));
        return documentType ;
    }
}
