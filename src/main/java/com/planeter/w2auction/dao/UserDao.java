package com.planeter.w2auction.dao;

import com.planeter.w2auction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);
    void deleteAllByUsername(List<String> username);
    @Query("SELECT i.id FROM Item AS i WHERE i.username = :username")
    List<Long> getItemIds(String username);
    @Query("SELECT o.id FROM Order AS o WHERE o.buyerId = :userId")
    List<Long> getOrderIds(Long userId);
}
