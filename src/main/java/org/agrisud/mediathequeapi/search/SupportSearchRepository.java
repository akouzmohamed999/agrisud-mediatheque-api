package org.agrisud.mediathequeapi.search;

import org.agrisud.mediathequeapi.model.Support;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SupportSearchRepository extends ElasticsearchRepository<Support, Long> {
    Support findOneBySupportId(Long supportId);
}
