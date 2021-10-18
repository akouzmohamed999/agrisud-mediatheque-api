package org.agrisud.mediathequeapi.model;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class Exposition {
    private Long expositionId;
    private Long categoryId;
    private String titleFr;
    private String titleEn;
    private String dateExposition;
    private String descriptionFr;
    private String descriptionEn;
    private Long countryId;
    //private Country country;
    private List<Thematic> listThematic;
    private List<ExpositionImage> listExpositionImage;

    public static Exposition baseMapper(ResultSet resultSet, int rowNumber) throws SQLException {
        Exposition exposition = new Exposition();
        exposition.setExpositionId(resultSet.getLong("exposition_id"));
        exposition.setCategoryId(resultSet.getLong("category_id"));
        exposition.setTitleFr(resultSet.getString("title_fr"));
        exposition.setTitleEn(resultSet.getString("title_en"));
        exposition.setDateExposition(resultSet.getString("date_exposition"));
        exposition.setDescriptionFr(resultSet.getString("description_fr"));
        exposition.setDescriptionEn(resultSet.getString("description_en"));
        exposition.setCountryId(resultSet.getLong("country_id"));
        exposition.setListThematic(new ArrayList<>());
        exposition.setListExpositionImage(new ArrayList<>());
        //exposition.setCountry(null);
        return exposition;
    }

}
