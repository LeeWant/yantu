package com.lee.yantu.repository;

import com.lee.yantu.Entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History,Integer> {
    List<History> findAllByYoosureId(Integer yoosureId);

    History findByUserIdAndYoosureIdAndOpFlag(Integer userId,Integer yoosureId,Integer opFlag);

    History findByUserIdAndYoosureIdAndIsFinish(Integer userId,Integer yoosureId,Integer isFinish);

    List<History> findAllByUserId(Integer userId);

    List findAllByUserIdAndIsFinish(Integer userId,Integer isFinish);
}
