package com.ems.UserService.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.UserService.Entity.User;
import com.ems.UserService.Utility.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmailIgnoreCase(String email);

	Optional<User> findByEmailIgnoreCase(String email);

	List<User> findByRole(Role enumRole);

}
