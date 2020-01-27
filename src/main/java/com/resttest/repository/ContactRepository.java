package com.resttest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.resttest.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{

	List<Contact> findByNameEquals(String name);
	
	Page<Contact> findAll(Pageable page);
	
	@Modifying
	@Transactional
	@Query("UPDATE Contact c SET c.favorite = :newFavorite WHERE c.id = :id")
	void updateContactSetFavoriteForId(@Param("id") Long id, @Param("newFavorite") Boolean favorite);
	
}
  