package com.lee.yantu.repository;

import com.lee.yantu.Entity.SystemTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemTagRepository extends JpaRepository<SystemTag,Integer> {

    List<SystemTag> findAllByTagFlag(Integer tagFlag);
}
