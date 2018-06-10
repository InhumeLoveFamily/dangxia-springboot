package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {
    Picture findById(int conId);
}
