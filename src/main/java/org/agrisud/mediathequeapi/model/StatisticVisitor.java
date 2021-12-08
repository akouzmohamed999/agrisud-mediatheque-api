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
public class StatisticVisitor {
	private Long statisticVisitorId;
	public Timestamp dateAction;
	
	public static StatisticVisitor baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
		StatisticVisitor statisticVisitor = new StatisticVisitor();
		statisticVisitor.setStatisticVisitorId(resultSet.getLong("statistic_visitor_id"));
		statisticVisitor.setDateAction(resultSet.getTimestamp("date_action"));
        return statisticVisitor;
    }
}
