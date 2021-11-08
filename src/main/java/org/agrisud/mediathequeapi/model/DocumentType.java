package org.agrisud.mediathequeapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document(indexName = "mediatheque")
public class DocumentType {
    @Id
    private String documentTypeESId;
    @Field(type = FieldType.Long)
    private Long documentTypeId;
    @Field(type = FieldType.Text)
    private String titleAnglais;
    @Field(type = FieldType.Text)
    private String titleFrancais;
    private String urlImage;
    private String pathImage;

    public static DocumentType baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        DocumentType documentType = new DocumentType();
        documentType.setDocumentTypeId(resultSet.getLong("document_type_id"));
        documentType.setTitleAnglais(resultSet.getString("title_anglais"));
        documentType.setTitleFrancais(resultSet.getString("title_francais"));
        documentType.setUrlImage(resultSet.getString("url_image"));
        documentType.setPathImage(resultSet.getString("path_image"));
        return documentType;
    }
}
