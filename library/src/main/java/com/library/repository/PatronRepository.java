package com.library.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.library.dto.PatronDTO;
import com.library.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<Patron,Long> {

     //Check Email Sent is Available
     @Query(value = "SELECT p.contact_information FROM librarymanagementsystem.patron  p WHERE CAST(p.contact_information AS jsonb) ->> 'email' = :email", nativeQuery = true)
      Optional<String> findByEmail(@Param("email") String email);


     @Query(value = "select * from librarymanagementsystem.patron p where cast (p.contact_information as jsonb) ->>'phone' = :phone",nativeQuery = true)
    Optional<Patron> findByPhone(String phone);
}
