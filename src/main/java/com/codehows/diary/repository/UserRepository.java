package com.codehows.diary.repository;

import com.codehows.diary.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //email로 사용자 정보를 가져온다.
    Optional<User> findByEmail(String email);
}
