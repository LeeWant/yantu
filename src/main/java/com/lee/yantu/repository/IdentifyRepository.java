package com.lee.yantu.repository;

import com.lee.yantu.Entity.Identification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentifyRepository extends JpaRepository<Identification,Integer> {
    Identification findByUserId(Integer userId);
}
