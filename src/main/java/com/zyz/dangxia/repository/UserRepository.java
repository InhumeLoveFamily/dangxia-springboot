package com.zyz.dangxia.repository;

import com.zyz.dangxia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findById(int id);
    User findByPhone(long phone);

    @Query("select name from User where id = ?1")
    String findName(int id);

}
