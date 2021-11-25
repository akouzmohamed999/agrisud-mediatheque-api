package org.agrisud.mediathequeapi.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class StatisticMedia {
	private Long statisticMediaId;
	private Long categoryId;
	private String typeCategory;
	private Long mediaId;
	private String action;
	public Timestamp dateAction;
	
	public static StatisticMedia baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
		StatisticMedia statisticMedia = new StatisticMedia();
		statisticMedia.setStatisticMediaId(resultSet.getLong("statistic_media_id"));
		statisticMedia.setCategoryId(resultSet.getLong("category_id"));
		statisticMedia.setTypeCategory(resultSet.getString("type_category"));
		statisticMedia.setMediaId(resultSet.getLong("media_id"));
		statisticMedia.setAction(resultSet.getString("action"));
		statisticMedia.setDateAction(resultSet.getTimestamp("date_action"));
        return statisticMedia;
    }
}
