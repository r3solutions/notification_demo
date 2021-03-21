package com.eventdriven.demo.repository;

import com.eventdriven.demo.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserMaster,Long> {

    UserMaster findByUsername(String username);
}
