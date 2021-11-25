package org.agrisud.mediathequeapi.model;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Document(indexName = "exposition")
@Setting(settingPath = "es/settings.json")
public class Exposition {
	@Id
    private String expositionESId;
	@Field(type = FieldType.Long)
    private Long expositionId;
    private Long categoryId;
    @Field(type = FieldType.Text, analyzer = "ngram_analyzer")
    private String titleFr;
    @Field(type = FieldType.Text, analyzer = "ngram_analyzer")
    private String titleEn;
    private String dateExposition;
    private String descriptionFr;
    private String descriptionEn;
    @Field(type = FieldType.Long)
    private Long countryId;
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Country> listCountry;
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Thematic> listThematic;
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<ExpositionImage> listExpositionImage;
    private Timestamp updateAt;
    private Integer numberView;
    private Integer numberDownload;

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
        return exposition;
    }

}
