package net.money.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.money.models.Family;
import net.money.models.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{
	List<Store> findByFamily(Family family);
	Optional<Store> findById(Long id);
}
