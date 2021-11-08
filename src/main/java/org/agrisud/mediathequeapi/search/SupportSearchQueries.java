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

    public SearchPage<Support> advancedSearch(String title, String countryId, String documentTypeId, String thematicId, String language, String dateSupport,Long categoryId, Pageable pageable) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (title != null && !"".equals(title)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("title", "*" + Utils.removeFrChars(title) + "*"));
        }
        if (countryId != null && !"".equals(countryId)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("listCountry.countryId", Long.parseLong(countryId)));
        }
        if (documentTypeId != null && !"".equals(documentTypeId)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("documentTypeId", Long.parseLong(documentTypeId)));
        }
        if(thematicId != null && !"".equals(thematicId)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("listThematic.thematicId", Long.parseLong(thematicId)));
        }
        if(language != null && !"".equals(language)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("language", language));
        }
        if(dateSupport != null && !"".equals(dateSupport) ){
            boolQueryBuilder.must(QueryBuilders.matchQuery("dateSupport", dateSupport));
        }
        if(categoryId != null && categoryId != 0) {
        	boolQueryBuilder.must(QueryBuilders.matchQuery("categoryId",categoryId));
        }
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
        return SearchHitSupport.searchPageFor(elasticsearchRestTemplate.search(searchQuery, Support.class), pageable);
        
    }
}
