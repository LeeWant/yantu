package com.lee.yantu.repository;

import com.lee.yantu.Entity.Evaluate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluateRepository extends JpaRepository<Evaluate,Integer> {
    Evaluate findByUserIdAndYoosureId(Integer userId,Integer yoosureId);
}
