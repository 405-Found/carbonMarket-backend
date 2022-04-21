package com.fof.found.carbonio;

import com.fof.found.carbonio.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    Page<User> findByEmail(String email, Pageable pageable);
}
