package org.agrisud.mediathequeapi.search;

import org.agrisud.mediathequeapi.model.SupportVideo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SupportVideoSearchRepository extends ElasticsearchRepository<SupportVideo, Long> {
    SupportVideo findOneBySupportId(Long supportId);
}
