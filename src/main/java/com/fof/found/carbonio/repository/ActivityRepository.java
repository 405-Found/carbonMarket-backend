package com.fof.found.carbonio.repository;

import com.fof.found.carbonio.entity.Activity;
import com.fof.found.carbonio.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivityRepository extends ElasticsearchRepository<Activity, String> {
    Page<Activity> findByUserID(UUID userID, Pageable pageable);
}
