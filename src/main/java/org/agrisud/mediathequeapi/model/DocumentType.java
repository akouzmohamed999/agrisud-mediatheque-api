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
    private String title;
    
    public static DocumentType baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
    	DocumentType documentType = new DocumentType();
    	documentType .setDocumentTypeId(resultSet.getLong("document_type_id"));
    	documentType .setTitle(resultSet.getString("title"));
        return documentType ;
    }
}
