package com.planeter.w2auction.dao;

import com.planeter.w2auction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

    @Query("SELECT i.id FROM Item AS i WHERE i.username = :username")
    List<Long> getItemIds(String username);

    @Query("SELECT o.id FROM Order AS o WHERE o.buyerId = :userId")
    List<Long> getOrderIds(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE User AS u SET u.salt =:salt WHERE u.username = :username")
    void updateSalt(String username, String salt);

    @Query("SELECT u.salt FROM User AS u WHERE u.username = :username")
    String getSalt(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User AS u SET u.password =:password WHERE u.username = :username")
    void updatePassWord(String username, String password);
}
