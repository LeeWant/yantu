package com.lee.yantu.repository;

import com.lee.yantu.Entity.Yoosure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface YoosureRepository extends JpaRepository<Yoosure,Integer> {
    List<Yoosure> findAllByIsDeleteAndIsJoinAndIsFinish(Integer isDelete,Integer isJoin,Integer isFinish);

    List<Yoosure> findAllByIsFinish(Integer isFinish);

    Page findAllByIsFinishAndIsDelete(Pageable pageable ,Integer isFinish,Integer isDelete);

    Page findByGoDateAfterAndIsDeleteAndIsJoinAndIsFinish(Pageable pageable,Date date,Integer isDelete,Integer isJoin,Integer isFinish);

    Page findByGoDateBeforeAndIsDeleteAndIsJoinAndIsFinish(Pageable pageable,Date date,Integer isDelete,Integer isJoin,Integer isFinish);

    Page findByTitleLikeAndIsDeleteAndIsJoinAndIsFinish(Pageable pageable,String keyword,Integer isDelete,Integer isJoin,Integer isFinish);

    Page findByTargetSchoolLikeAndIsDeleteAndIsJoinAndIsFinish(Pageable pageable,String keyword,Integer isDelete,Integer isJoin,Integer isFinish);

    Page findByPlaceLikeAndIsDeleteAndIsJoinAndIsFinish(Pageable pageable,String keyword,Integer isDelete,Integer isJoin,Integer isFinish);

    Page findByCostGreaterThanEqualAndIsDeleteAndIsJoinAndIsFinish(Pageable pageable, BigDecimal cost, Integer isDelete, Integer isJoin, Integer isFinish);

    Page findByCostLessThanEqualAndIsDeleteAndIsJoinAndIsFinish(Pageable pageable,BigDecimal cost,Integer isDelete,Integer isJoin,Integer isFinish);
}
