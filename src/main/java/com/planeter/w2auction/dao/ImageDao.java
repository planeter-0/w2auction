package com.planeter.w2auction.dao;

import com.planeter.w2auction.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDao extends JpaRepository<Image,Long> {
}
