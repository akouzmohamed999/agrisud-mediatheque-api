package org.agrisud.mediathequeapi.search;

import org.agrisud.mediathequeapi.model.Exposition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ExpositionSearchRepository extends ElasticsearchRepository<Exposition, Long> {
    Exposition findOneByExpositionId(Long supportId);
}
