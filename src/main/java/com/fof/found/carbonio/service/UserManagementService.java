package com.fof.found.carbonio.service;

import com.fof.found.carbonio.UserRepository;
import com.fof.found.carbonio.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {
    @Autowired
    UserRepository userRepository;

    public User findUserByEmail(String email){
        Page<User> page = userRepository.findByEmail(email, Pageable.ofSize(1));
        return page.getContent().isEmpty() ? null : page.getContent().get(0);
    }

    public void registerUser(User user){
        userRepository.save(user);
    }
}
