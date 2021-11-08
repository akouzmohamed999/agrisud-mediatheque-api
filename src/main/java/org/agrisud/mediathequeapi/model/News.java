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
public class News {
	private Long newsId;
	private Long supportId;
	private String typeCategory;
	
	public static News baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
		News news= new News();
		news.setNewsId(resultSet.getLong("news_id"));
		news.setSupportId(resultSet.getLong("support_id"));
		news.setTypeCategory(resultSet.getString("type_category"));
        return news;
    }
}
