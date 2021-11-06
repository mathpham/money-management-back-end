package net.money.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.money.models.ERole;
import net.money.models.Role;

@Repository
public interface RoleRepositoty extends JpaRepository<Role, Integer>{
	Optional<Role> findByName(ERole name);
}
