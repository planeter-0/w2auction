package com.planeter.w2auction.dao;

import com.planeter.w2auction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);
    void deleteAllByUsername(List<String> username);
}
