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
	private String countryName;
	private String codeAlpha;
	private String urlImage;
	private String pathImage;
	
	public static Country baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
		Country country = new Country();
		country.setCountryId(resultSet.getLong("country_id"));
        country.setCountryName(resultSet.getString("country_name"));
        country.setCodeAlpha(resultSet.getString("code_alpha"));
        country.setPathImage(resultSet.getString("path_image"));
        country.setUrlImage(resultSet.getString("url_image"));
        return country;
    }
}
