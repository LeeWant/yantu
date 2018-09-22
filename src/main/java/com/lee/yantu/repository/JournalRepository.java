package com.lee.yantu.repository;

import com.lee.yantu.Entity.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository extends JpaRepository<Journal,Integer> {

    List<Journal> findAllByUserIdAndIsDelete(Integer userId, Integer isDelete);

    Journal findByJournalIdAndIsDelete(Integer journalId,Integer isDelete);

    Page findByIsOpenAndIsDelete(Pageable pageable,Integer isOpen,Integer isDelete);
}
