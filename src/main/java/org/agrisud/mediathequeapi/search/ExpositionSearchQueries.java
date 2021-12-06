package org.agrisud.mediathequeapi.search;

import org.agrisud.mediathequeapi.model.Exposition;
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
public class ExpositionSearchQueries {
	@Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    public SearchPage<Exposition> advancedSearch(String titleFr,String titleEn, String countryId, String thematicId, String dateSupport,Long categoryId, Pageable pageable) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (titleFr != null && !"".equals(titleFr)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("titleFr", "*" + Utils.removeFrChars(titleFr) + "*"));
        }
        if (titleEn != null && !"".equals(titleEn)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("titleEn", "*" + Utils.removeFrChars(titleEn) + "*"));
        }
        if (countryId != null && !"".equals(countryId)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("countryId", Long.parseLong(countryId)));
        }
        if(thematicId != null && !"".equals(thematicId)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("listThematic.thematicId", Long.parseLong(thematicId)));
        }
        if(dateSupport != null && !"".equals(dateSupport) ){
            boolQueryBuilder.must(QueryBuilders.matchQuery("dateSupport", dateSupport));
        }
        if(categoryId != null && categoryId != 0) {
        	boolQueryBuilder.must(QueryBuilders.matchQuery("categoryId",categoryId));
        }
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageable)
                .build();
        return SearchHitSupport.searchPageFor(elasticsearchRestTemplate.search(searchQuery, Exposition.class), pageable);
    }
}
