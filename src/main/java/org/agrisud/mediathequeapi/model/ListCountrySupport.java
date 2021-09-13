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
public class ListCountrySupport {
	private Long listCountrySupportId;
    private String countryCode;
    private Long supportId;
    
    public static ListCountrySupport baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
    	ListCountrySupport listCountrySupport = new ListCountrySupport();
    	listCountrySupport.setListCountrySupportId(resultSet.getLong("list_country_support_id"));
    	listCountrySupport.setCountryCode(resultSet.getString("Country_code"));
    	listCountrySupport.setSupportId(resultSet.getLong("support_id"));
        return listCountrySupport;
    }

}
