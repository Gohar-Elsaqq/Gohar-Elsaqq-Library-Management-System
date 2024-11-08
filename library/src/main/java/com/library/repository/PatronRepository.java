package com.library.repository;

import com.library.dto.ContactInfoDTO;
import com.library.dto.PatronDTO;
import com.library.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<Patron,Long> {


//     @Query(value = "SELECT p.contact_information ->> 'email' as email FROM librarymanagementsystem.patron p WHERE p.contact_information ->> 'email' = :email", nativeQuery = true)

     //Check Email Sent is Available
     @Query(value = "SELECT p.contact_information FROM librarymanagementsystem.patron  p WHERE CAST(p.contact_information AS jsonb) ->> 'email' = :email", nativeQuery = true)
      Optional<String> findByEmail(@Param("email") String email);


}
