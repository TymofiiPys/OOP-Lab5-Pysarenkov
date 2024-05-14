package com.restaurant.repository;

import com.restaurant.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select Client.email from Client where Client.id = ?1")
    Optional<String> findEmailAddressById(Long id);
    Optional<Client> findByEmail(String email);
}
