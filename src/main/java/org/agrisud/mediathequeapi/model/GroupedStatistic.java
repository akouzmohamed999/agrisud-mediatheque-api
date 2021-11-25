package org.agrisud.mediathequeapi.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
public class GroupedStatistic {
	private Integer month;
	private Integer count;
	private Integer year;
	
	public static GroupedStatistic baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
		GroupedStatistic statisticMonthly= new GroupedStatistic();
		statisticMonthly.setMonth(resultSet.getInt("month"));
		statisticMonthly.setCount(resultSet.getInt("count"));
		statisticMonthly.setYear(resultSet.getInt("year"));
        return statisticMonthly;
    }
}
