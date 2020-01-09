package com.resttest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resttest.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{

	Optional<Contact> findByNameEquals(String name);
}
