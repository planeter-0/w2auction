package com.planeter.w2auction.dao;

import com.planeter.w2auction.entity.ESItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Planeter
 * @description: TODO
 * @date 2021/5/5 10:54
 * @status dev
 */
public interface ESItemDao extends ElasticsearchRepository<ESItem, Long> {
    Page<ESItem> findByNameAndDetailAndTagsAndVerifiedIsTrue(String name, String detail, String tags, Pageable pageable);
}
