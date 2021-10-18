package fr.vvlabs.hibernate.sample.repository;

import fr.vvlabs.hibernate.sample.model.Moto;
import fr.vvlabs.hibernate.sample.model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Integer> {
}
