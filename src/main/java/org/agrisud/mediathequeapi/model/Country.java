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
public class Country {
	private Long countryId;
	private String titleAnglais;
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
