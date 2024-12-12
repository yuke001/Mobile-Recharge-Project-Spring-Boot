package com.mobile.Recharge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobile.Recharge.dto.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);

}
