package fr.vvlabs.hibernate.sample.repository;

import fr.vvlabs.hibernate.sample.model.Vehicule;
import fr.vvlabs.hibernate.sample.model.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Integer> {
}
