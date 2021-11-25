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
public class StatisticData {
	private Integer total;
	private Integer numberDocument;
	private Integer numberVideo;
	private Integer numberExposition;
	
	public static StatisticData baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
		StatisticData statisticData= new StatisticData();
		statisticData.setTotal(resultSet.getInt("total"));
		statisticData.setNumberDocument(resultSet.getInt("number_document"));
		statisticData.setNumberVideo(resultSet.getInt("number_video"));
		statisticData.setNumberExposition(resultSet.getInt("number_exposition"));
        return statisticData;
    }
}