package com.fof.found.carbonio.repository;

import com.fof.found.carbonio.entity.Tip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipsRepository extends ElasticsearchRepository<Tip,String> {
    Page<Tip> findBy(String catagory, Pageable pageable);
}
