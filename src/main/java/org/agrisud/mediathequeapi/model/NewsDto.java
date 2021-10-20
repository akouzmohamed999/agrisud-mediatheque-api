package org.agrisud.mediathequeapi.model;

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
public class NewsDto {
	private Long supportId;
    private String title;
    private String pathSupport;
    private String pathImage;
    private String urlImage;
    private String urlSupport;
    private String language;
    private List<Country> listCountry;
    private List<Thematic> listThematic;
    private DocumentType documentType;
    private VideoType videoType;
    private String dateSupport;
    private boolean download;
}
