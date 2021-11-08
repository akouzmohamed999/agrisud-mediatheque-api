package org.agrisud.mediathequeapi.model;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ExpositionImage {
    private Long expositionImageId;
    private String name;
    private String urlImage;
    private String pathImage;
    private Long rankImg;

    public static ExpositionImage baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        ExpositionImage expositionImage = new ExpositionImage();
        expositionImage.setExpositionImageId(resultSet.getLong("exposition_image_id"));
        expositionImage.setName(resultSet.getString("name"));
        expositionImage.setUrlImage(resultSet.getString("url_image"));
        expositionImage.setPathImage(resultSet.getString("path_image"));
        expositionImage.setRankImg(resultSet.getLong("rank_img"));
        return expositionImage;
    }

}
