package net.money.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.money.models.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Integer>{
	 Optional<Family> findById(Integer id);
}
