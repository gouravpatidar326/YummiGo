package com.gourav.YummiGoBackend.repo;

import com.gourav.YummiGoBackend.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepo extends JpaRepository<FoodEntity, String> {

    Optional<FoodEntity> findById(String id);

}
