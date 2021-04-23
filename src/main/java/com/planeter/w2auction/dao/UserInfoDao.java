package com.planeter.w2auction.dao;

import com.planeter.w2auction.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UserInfoDao extends JpaRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
    boolean existsByUsername(String username);
}
