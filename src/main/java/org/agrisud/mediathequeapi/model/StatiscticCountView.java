package org.agrisud.mediathequeapi.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
public class StatiscticCountView {
	private Integer numberView;
	private Integer numberDownload;
	
	public static StatiscticCountView baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
		StatiscticCountView statiscticCountView= new StatiscticCountView();
		statiscticCountView.setNumberView(resultSet.getInt("number_view"));
		statiscticCountView.setNumberDownload(resultSet.getInt("number_download"));
        return statiscticCountView;
    }
}
