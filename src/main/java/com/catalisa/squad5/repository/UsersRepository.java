package com.catalisa.squad5.repository;

import com.catalisa.squad5.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    UserDetails findByUsername(String username);
}
