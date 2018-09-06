package com.lee.yantu.repository;

import com.lee.yantu.Entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner,Integer> {


    List<Banner> findByIsOnlineAndIsDelete(Integer isOnline,Integer isDelete);

}
