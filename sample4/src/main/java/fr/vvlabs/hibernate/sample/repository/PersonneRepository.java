package fr.vvlabs.hibernate.sample.repository;

import fr.vvlabs.hibernate.sample.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Integer> {
}
