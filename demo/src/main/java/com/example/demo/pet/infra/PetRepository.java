package com.example.demo.pet.infra;

import com.example.demo.pet.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select p from Pet p where p.owner.id = :ownerId")
    List<Pet> findByOwnerId(@Param("ownerId") Long ownerId);

    @Query("select p from Pet p where p.id = :id and p.owner.id = :ownerId")
    Optional<Pet> findByIdAndOwnerId(@Param("id") Long id, @Param("ownerId") Long ownerId);

    @Query("select (count(p) > 0) from Pet p where p.id = :id and p.owner.id = :ownerId")
    boolean existsByIdAndOwnerId(@Param("id") Long id, @Param("ownerId") Long ownerId);

    @Modifying
    @Query("delete from Pet p where p.id = :id and p.owner.id = :ownerId")
    void deleteByIdAndOwnerId(@Param("id") Long id, @Param("ownerId") Long ownerId);
}
