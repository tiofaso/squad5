package com.catalisa.squad5.repository;

import com.catalisa.squad5.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
