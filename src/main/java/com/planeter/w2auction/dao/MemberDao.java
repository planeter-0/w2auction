package com.planeter.w2auction.dao;

import com.planeter.w2auction.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDao extends JpaRepository<Member,Long> {
}
