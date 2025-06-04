package com.ems.UserService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.UserService.Entity.Organizer;


public interface OrganizerRepository extends JpaRepository<Organizer, Integer> {
    boolean existsByEmailIgnoreCase(String email);

	Optional<Organizer> findByEmailIgnoreCase(String email);
}

