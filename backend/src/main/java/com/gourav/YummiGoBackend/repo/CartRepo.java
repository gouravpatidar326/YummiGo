package com.gourav.YummiGoBackend.repo;

import com.gourav.YummiGoBackend.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartEntity, String> {

    Optional<CartEntity> findByUserId(String userId);

    void deleteByUserId(String userId);

}
