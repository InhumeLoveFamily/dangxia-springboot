package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword,Integer> {
    @Query("from Keyword where id <> '-1'")
    List<Keyword> findUserful();
}
