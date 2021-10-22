package org.agrisud.mediathequeapi.search;

import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.util.Utils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class SupportSearchQueries {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    public SearchPage<Support> advancedSearch(String title, Long countryId, Long documentTypeId, Long thematicId, String language, String dateSupport, Pageable pageable) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (title != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("title", "*" + Utils.removeFrChars(title) + "*"));
        }

        if (countryId != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("listCountry.countryId", countryId));
        }

        if (documentTypeId != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("documentTypeId", documentTypeId));
        }

        if(thematicId != null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("listThematic.thematicId", thematicId));
        }

        if(language != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("language", language));
        }

        if(dateSupport != null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("dateSupport", dateSupport));
        }

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        return SearchHitSupport.searchPageFor(elasticsearchRestTemplate.search(searchQuery, Support.class), pageable);
    }
}
