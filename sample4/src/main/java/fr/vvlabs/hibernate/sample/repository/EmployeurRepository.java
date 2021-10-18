package fr.vvlabs.hibernate.sample.repository;

import fr.vvlabs.hibernate.sample.model.Employeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeurRepository extends JpaRepository<Employeur, Integer> {
}
