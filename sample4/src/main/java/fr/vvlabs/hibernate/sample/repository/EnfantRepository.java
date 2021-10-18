package fr.vvlabs.hibernate.sample.repository;

import fr.vvlabs.hibernate.sample.model.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnfantRepository extends JpaRepository<Enfant, Integer> {
}
