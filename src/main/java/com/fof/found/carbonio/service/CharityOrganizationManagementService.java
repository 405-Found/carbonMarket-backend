package com.fof.found.carbonio.service;

import com.fof.found.carbonio.entity.CharityOrganization;
import com.fof.found.carbonio.repository.CharityOrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class CharityOrganizationManagementService {
    @Autowired
    CharityOrganizationRepository charityOrganizationRepository;
    @Autowired
    ElasticsearchRestTemplate restTemplate;

    public CharityOrganization findByName(String name){
        Page<CharityOrganization> page = charityOrganizationRepository.findByName(name, Pageable.ofSize(1));
        return page.getContent().get(0);
    }
    public List<CharityOrganization> findByKeyword(String keyword){
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("description",keyword))
                .build();
        SearchHits<CharityOrganization> searchHits = restTemplate.search(searchQuery,CharityOrganization.class, IndexCoordinates.of("charityorg"));
        return searchHits.getSearchHits().stream().map(x->x.getContent()).collect(Collectors.toList());
    }
    public List<CharityOrganization> findAll(){
        List<CharityOrganization> orgs = new ArrayList<>();
        charityOrganizationRepository.findAll().forEach(orgs::add);
        return orgs;
    }


}
