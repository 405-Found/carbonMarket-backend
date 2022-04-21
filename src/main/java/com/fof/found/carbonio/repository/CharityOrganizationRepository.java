package com.fof.found.carbonio.repository;

import com.fof.found.carbonio.entity.CharityOrganization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CharityOrganizationRepository extends ElasticsearchRepository<CharityOrganization,String> {
    Page<CharityOrganization> findByName(String name, Pageable pageable);
}
