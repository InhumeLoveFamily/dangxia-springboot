package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {
    Picture findById(int conId);

    @Query(nativeQuery = true,value = "select B.url from picture B where B.id = (select A.icon_id from user A where A.id = ?1)")
    String findPath(int userId);

}
