package com.lee.yantu.repository;

import com.lee.yantu.Entity.UserDefinedTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserDefinedTagRepository extends JpaRepository<UserDefinedTag, Integer> {
    List<UserDefinedTag> findByUserId(Integer userId);

    void deleteAllByUserId(Integer userId);

}
