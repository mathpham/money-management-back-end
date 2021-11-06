package net.money.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import net.money.models.ExpenseType;
import net.money.models.Family;

@RestController
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Integer>{
	List<ExpenseType> findByFamily(Family family);
	Optional<ExpenseType> findById(Long id);
}
