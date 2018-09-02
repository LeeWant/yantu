package com.lee.yantu.repository;

import com.lee.yantu.Entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img,Integer> {
    List<Img> findAllByYoosureId(Integer yoosureId);

    List<Img> findAllByJournalId(Integer journalId);
}
