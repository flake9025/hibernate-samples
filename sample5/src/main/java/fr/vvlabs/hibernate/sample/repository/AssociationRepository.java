package fr.vvlabs.hibernate.sample.repository;

import fr.vvlabs.hibernate.sample.model.Association;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Integer> {
}
