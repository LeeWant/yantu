package com.lee.yantu.repository;

import com.lee.yantu.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);

    List<User> findAllByJoinId(Integer yoosureId);

    User findByNickName(String nickName);

    Page findByNickNameLikeAndIsDelete(Pageable pageable,String nickName,Integer isDelete);

}
