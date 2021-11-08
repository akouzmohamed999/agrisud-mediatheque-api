package org.agrisud.mediathequeapi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

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
public class Country {
	@Id
	private String countryESId;
	@Field(type = FieldType.Long)
	private Long countryId;
	@Field(type = FieldType.Text)
	private String titleAnglais;
	@Field(type = FieldType.Text)
	private String titleFrancais;
	private String urlImage;
	private String pathImage;
	
	public static Country baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
		Country country = new Country();
		country.setCountryId(resultSet.getLong("country_id"));
		country.setTitleAnglais(resultSet.getString("title_anglais"));
		country.setTitleFrancais(resultSet.getString("title_francais"));
		country.setPathImage(resultSet.getString("path_image"));
		country.setUrlImage(resultSet.getString("url_image"));
        return country;
    }
}
